package com.backend.project.kafkaConsumer;

import com.backend.project.Elasticsearch.Conversations.Conversation;
import com.backend.project.Elasticsearch.Conversations.ConversationController;
import com.backend.project.Elasticsearch.Posts.Post;
import com.backend.project.Elasticsearch.Posts.PostController;
import com.backend.project.Redis.RedisService;
import com.backend.project.mongo.Controller;
import com.backend.project.mongo.RegexRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Component
public class ConsumePosts implements Runnable{

    @Autowired
    KafkaConfigConsumer kafkaConfigConsumer;

    @Autowired
    ConversationController conversationController;

    @Autowired
    PostController postController;

    @Override
    @KafkaListener(topics = "Posts", groupId = "ConsumerGRP")
    public void run() {
        KafkaConsumer<String, Post> consumer =new KafkaConsumer<String, Post>(kafkaConfigConsumer.props);
        consumer.subscribe(Collections.singletonList("Posts"));

        try {
            while (true) {
                ConsumerRecords<String, Post> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, Post> record : records) {
                    System.out.println(" received: " + record.value().toString());

                    Post post=record.value();
                    if(matchRegex(post.getPostObject().get("text").toString())){

                        //add to redis and es (both indexes)
                        RedisService.addToSet(post.getPostId());
                        postController.addPost(post);

                        conversationController.addConversation(postFormatChange(post));


                    }
                }

                consumer.commitAsync();
            }
        } finally {
            consumer.close();
        }
    }


    private boolean matchRegex(String title){
        List<RegexRecord> regexes= Controller.getAllRegex();
        for ( RegexRecord regexRecord : regexes){
            if (title.matches(regexRecord.getRegex())){
                return true;
            }
        }
        return false;
    }

    private Conversation postFormatChange(Post post){
        Conversation conversation=new Conversation(post.getPostId(),post.getPostObject().get("post_id").toString(),post.getPostObject());
        return  conversation;
    }

}

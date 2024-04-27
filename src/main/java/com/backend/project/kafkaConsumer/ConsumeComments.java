package com.backend.project.kafkaConsumer;

import com.backend.project.Elasticsearch.Conversations.Conversation;
import com.backend.project.Elasticsearch.Conversations.ConversationController;
import com.backend.project.Redis.RedisService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;

@Component
public class ConsumeComments implements Runnable {

    @Autowired
    KafkaConfigConsumer kafkaConfigConsumer;

    @Autowired
    ConversationController conversationController;


    @Override
    @KafkaListener(topics = "Comments", groupId = "ConsumerGRP")
    public void run() {
        KafkaConsumer<String, Conversation> consumer =new KafkaConsumer<String, Conversation>(kafkaConfigConsumer.props);
        consumer.subscribe(Collections.singletonList("Comments"));

        try {
            while (true) {
                ConsumerRecords<String, Conversation> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, Conversation> record : records) {
                    System.out.println(" received: " + record.value().toString());

                    Conversation conversation=record.value();
                    if (isPresent(conversation.getPostId())){

                        // add to es
                        conversationController.addConversation(conversation);
                    }
                }

                consumer.commitAsync();
            }
        } finally {
            consumer.close();
        }
    }


    private boolean isPresent(String id){
        return RedisService.isParentInteresting(id);
    }
}

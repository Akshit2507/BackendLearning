package com.backend.project.kafkaProducer;

import com.backend.project.Elasticsearch.Posts.Post;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class ProducePosts {

    final KafkaConfigProducer kafkaConfigProducer;

    @Autowired
    public ProducePosts(KafkaConfigProducer kafkaConfigProducer) {
        this.kafkaConfigProducer = kafkaConfigProducer;

    }

    public void writePost(Post postObject){
        KafkaProducer producer=new KafkaProducer(kafkaConfigProducer.kafkaProps);
        ProducerRecord<String, Post> record = new ProducerRecord<>("Posts", postObject);

        try {
            producer.send(record);
        }catch (Exception e){
            System.out.println("post produce m error");
            e.printStackTrace();
        }
    }
}

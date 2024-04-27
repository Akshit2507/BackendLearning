package com.backend.project.kafkaProducer;

import com.backend.project.Elasticsearch.Conversations.Conversation;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.elasticsearch.common.inject.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProduceComments{

    final KafkaConfigProducer kafkaConfigProducer;

    @Autowired
    public ProduceComments(KafkaConfigProducer kafkaConfigProducer) {
        this.kafkaConfigProducer = kafkaConfigProducer;
    }

    public void writeComments(Conversation commentObject){
        KafkaProducer producer =new KafkaProducer<>(kafkaConfigProducer.kafkaProps);
        ProducerRecord<String, Conversation> record = new ProducerRecord<>("Comments", commentObject);
        try {
            producer.send(record);
        }catch (Exception e){
            System.out.println("comment produce m error");
            e.printStackTrace();
        }
    }
}

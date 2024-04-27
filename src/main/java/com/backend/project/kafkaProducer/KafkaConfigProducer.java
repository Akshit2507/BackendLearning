package com.backend.project.kafkaProducer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConfigProducer {

    public static Properties kafkaProps;

    public KafkaConfigProducer(){

        kafkaProps=new Properties();
        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        kafkaProps.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
    }
}

package com.backend.project.kafkaConsumer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Configuration
@Component
public class KafkaConfigConsumer {
    public static Properties props;

    public KafkaConfigConsumer(){

        props=new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("group.id", "ConsumerGRP");
    }
}
package com.jql.kafka.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.Future;

@Slf4j
public class KafkaProductor {

    private static final String BROKER_LIST = "127.0.0.1:9092";
    private static KafkaProducer<String,String> producer = null;

    static {

        Properties properties = initConfig();
        producer = new KafkaProducer<String, String>(properties);

    }

    private static Properties initConfig(){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BROKER_LIST);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        return properties;
    }

    public static void send(String topic,String message){
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,message);
        Future<RecordMetadata> result = producer.send(record, (recordMetadata, e) -> {
            if (null != e) {
                log.error("kafka send error ", e);
            } else {
                log.info("kafka send success,topic={}, message={}.", topic, message);
            }
        });
    }

}

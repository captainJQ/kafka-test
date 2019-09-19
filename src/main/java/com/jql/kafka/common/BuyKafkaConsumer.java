package com.jql.kafka.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Arrays;
import java.util.List;
@Slf4j
public class BuyKafkaConsumer extends KafkaConsumer implements Runnable{

    private static final String TOPIC = "buy";

    private static final org.apache.kafka.clients.consumer.KafkaConsumer consumer ;

    static{
        consumer = new org.apache.kafka.clients.consumer.KafkaConsumer(initConfig());
        List<String> topics = Arrays.asList(TOPIC);
        consumer.subscribe(topics);
    }

    @Override
    public void consumer() {
        ConsumerRecords<String,String> records = consumer.poll(10);
        for (ConsumerRecord<String,String> record: records) {
            String topic = record.topic();
            String message = record.value();
            log.info("kafka consumer success,topic={}, message={}.",topic,message);
        }
    }

    @Override
    public void run() {
        while (true){
            consumer();
        }
    }
}

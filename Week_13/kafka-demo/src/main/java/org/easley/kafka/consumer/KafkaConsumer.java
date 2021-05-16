package org.easley.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = {"testkafka"})
    public void onMessage(ConsumerRecord<?, ?> record) {
        System.out.printf("topic：%s，partition：%s，value：%s\n", record.topic(), record.partition(), record.value());
    }
}


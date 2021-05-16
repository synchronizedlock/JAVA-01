package org.easley.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class KafkaProducer {

    private KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<?, ?> kafkaTemplate) {
        this.kafkaTemplate = (KafkaTemplate<String, Object>) kafkaTemplate;
    }

    @Scheduled(cron = "0/3 * * * * ?")
    private void sendMessage() {
        kafkaTemplate.send("testkafka", "helloworld");
    }
}

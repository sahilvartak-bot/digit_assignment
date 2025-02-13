package digit.academy.tutorial.kafka;

import digit.academy.tutorial.web.models.Advocate;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.kafka.CustomKafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// NOTE: If tracer is disabled change CustomKafkaTemplate to KafkaTemplate in autowiring

@Component
@Slf4j
public class AdvocateKafkaProducer {

    private final CustomKafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.advocate.create}")
    private String advocateCreateTopic;

    @Autowired
    public AdvocateKafkaProducer(CustomKafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publishes advocate data to Kafka for persisting.
     */
    public void sendAdvocateCreateEvent(Advocate advocate) {
        log.info("Publishing Advocate Registration event to Kafka: {}", advocate);
        kafkaTemplate.send(advocateCreateTopic, advocate);
    }
}


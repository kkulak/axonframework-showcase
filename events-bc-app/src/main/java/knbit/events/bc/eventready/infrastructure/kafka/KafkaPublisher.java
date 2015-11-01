package knbit.events.bc.eventready.infrastructure.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * Created by novy on 01.11.15.
 */

public class KafkaPublisher {

    private final static String TOPIC = "EventTookPlaceEvent";
    private final KafkaProducer<String, byte[]> producer;

    public KafkaPublisher(KafkaProducer<String, byte[]> producer) {
        this.producer = producer;
    }

    public void publish(EventTookPlace eventTookPlace) {
        final ProducerRecord<String, byte[]> msg =
                new ProducerRecord<>(TOPIC, eventTookPlace.asBytes());

        producer.send(msg);
    }
}
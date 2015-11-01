package knbit.events.bc.eventready.infrastructure.kafka.config;

import knbit.events.bc.common.Profiles;
import knbit.events.bc.eventready.infrastructure.kafka.KafkaPublisher;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Properties;

/**
 * Created by novy on 01.11.15.
 */

@Profile(Profiles.PROD)
@Configuration
class KafkaConfig {

    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers;

    @Bean(destroyMethod = "close")
    public KafkaProducer<String, byte[]> kafkaProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");

        return new KafkaProducer<>(props);
    }

    @Bean
    public KafkaPublisher kafkaPublisher(KafkaProducer<String, byte[]> producer) {
        return new KafkaPublisher(producer);
    }
}

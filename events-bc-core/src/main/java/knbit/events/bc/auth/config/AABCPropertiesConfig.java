package knbit.events.bc.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by novy on 25.07.15.
 */

@Configuration
@EnableConfigurationProperties
public class AABCPropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "aa-bc")
    @Profile("!mock")
    public AABCProperties aabcProperties() {
        return new AABCProperties();
    }

    @Data
    static class AABCProperties {
        private String authenticationEndpoint;
        private String authorizationEndpoint;
        private String authHeaderKey;
    }
}

package knbit.events.bc.auth.config;

import knbit.events.bc.auth.aabcclient.clients.AABCClient;
import knbit.events.bc.auth.aabcclient.clients.mock.MockAABCClient;
import knbit.events.bc.auth.aabcclient.clients.rest.RestAABCClient;
import knbit.events.bc.common.Profiles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import static knbit.events.bc.auth.config.AABCPropertiesConfig.AABCProperties;

/**
 * Created by novy on 25.07.15.
 */

@Configuration
@Import(AABCPropertiesConfig.class)
@Slf4j
class AABCClientConfig {

    @Bean
    @Profile(Profiles.PROD)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Profile(Profiles.PROD)
    public AABCClient aabcClient(AABCProperties aabcProperties, RestTemplate restTemplate) {
        log.debug("creating rest aabcClient with props {}", aabcProperties.toString());
        return new RestAABCClient(
                aabcProperties.getAuthenticationEndpoint(),
                aabcProperties.getAuthorizationEndpoint(),
                aabcProperties.getAuthHeaderKey(),
                restTemplate
        );
    }

    @Bean
    @Profile(Profiles.DEV)
    public AABCClient mockAabcClient() {
        log.debug("creating mock aabcClient");
        return new MockAABCClient();
    }
}

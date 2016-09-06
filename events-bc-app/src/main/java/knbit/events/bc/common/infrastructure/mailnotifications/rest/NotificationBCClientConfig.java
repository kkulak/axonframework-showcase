package knbit.events.bc.common.infrastructure.mailnotifications.rest;

import knbit.events.bc.auth.aabcclient.clients.AABCClient;
import knbit.events.bc.common.Profiles;
import knbit.events.bc.common.infrastructure.mailnotifications.NotificationBCClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

/**
 * Created by novy on 05.12.15.
 */

@Profile(Profiles.PROD)
@Configuration
@EnableConfigurationProperties
@Slf4j
class NotificationBCClientConfig {

    @Bean
    public NotificationBCClient restNotificationBCClient(AABCClient aabcClient, NotificationBCProperties props) {
        log.debug("creating notification bc client with properties " + props);
        final RestTemplate notificationRestTemplate = new RestTemplate();
        final TokenProvider tokenProvider = () -> aabcClient.obtainToken(props.getKnbitServiceId(), props.getKnbitPassword());

        return new RestNotificationBCClient(tokenProvider, props, notificationRestTemplate);
    }

    @Bean
    @ConfigurationProperties(prefix = "notification-bc")
    public NotificationBCProperties notificationBCConfiguration() {
        return new NotificationBCProperties();
    }
}

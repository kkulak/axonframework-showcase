package knbit.events.bc.common.mailnotifications;

import knbit.events.bc.auth.aabcclient.clients.AABCClient;
import knbit.events.bc.common.Profiles;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * Created by novy on 05.12.15.
 */

@Slf4j
@Configuration
class NotificationBCClientConfig {

    // todo fixme
//    @Bean
//    @Profile(Profiles.PROD)
//    public NotificationBCClient restNotificationBCClient(AABCClient aabcClient, RestTemplate restTemplate) {
//        return null;
//    }

    @Bean
    @Profile(Profiles.DEV)
    public NotificationBCClient mockNotificationBCClient() {
        log.debug("creating mock notificationBCClient");
        return new MockNotificationBCClient();
    }

    @Slf4j
    private static class MockNotificationBCClient implements NotificationBCClient {
        @Override
        public void sendNotificationToAllMembers(Notification notification) {
            log.debug(String.format("about to send %s to all knbit members", notification));
        }

        @Override
        public void sendNotificationTo(Collection<MemberId> members, Notification notification) {
            log.debug(String.format("about to send %s to %s", notification, members));
        }
    }
}

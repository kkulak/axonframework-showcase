package knbit.events.bc.common.infrastructure.mailnotifications.mock;

import knbit.events.bc.common.Profiles;
import knbit.events.bc.common.infrastructure.mailnotifications.Notification;
import knbit.events.bc.common.infrastructure.mailnotifications.NotificationBCClient;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Collection;

/**
 * Created by novy on 06.12.15.
 */

@Slf4j
class MockNotificationBCClient implements NotificationBCClient {
    @Override
    public void sendNotificationToAllMembers(Notification notification) {
        log.debug(String.format("about to send %s to all knbit members", notification));
    }

    @Override
    public void sendNotificationTo(Collection<MemberId> members, Notification notification) {
        log.debug(String.format("about to send %s to %s", notification, members));
    }
}

@Profile(Profiles.DEV)
@Configuration
@Slf4j
class MockNotificationBCClientConfig {

    @Bean
    public NotificationBCClient mockNotificationBCClient() {
        log.debug("creating mock notificationBCClient");
        return new MockNotificationBCClient();
    }

}



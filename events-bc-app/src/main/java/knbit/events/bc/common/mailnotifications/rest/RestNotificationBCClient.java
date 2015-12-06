package knbit.events.bc.common.mailnotifications.rest;

import knbit.events.bc.common.mailnotifications.Notification;
import knbit.events.bc.common.mailnotifications.NotificationBCClient;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by novy on 06.12.15.
 */

@RequiredArgsConstructor
public class RestNotificationBCClient implements NotificationBCClient {

    private final TokenProvider tokenProvider;
    private final NotificationBCConfiguration configuration;
    private final RestTemplate restTemplate;

    @Override
    public void sendNotificationToAllMembers(Notification notification) {
        tokenProvider
                .provideToken()
                .ifPresent(token -> sendNotification(
                        token,
                        configuration.getNotifyAllMembersEndpoint(),
                        new NotificationWithoutMembersJson(notification)
                ));
    }

    @Override
    public void sendNotificationTo(Collection<MemberId> members, Notification notification) {
        tokenProvider
                .provideToken()
                .ifPresent(token -> sendNotification(
                        token,
                        configuration.getNotifyMembersEndpoint(),
                        new NotificationWithMembersJson(notification, members)
                ));
    }

    private void sendNotification(String token, String notificationUrl, NotificationJson notification) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set(tokenProvider.tokenHeaderName(), token);

        restTemplate.postForObject(notificationUrl, new HttpEntity<>(notification, headers), Void.class);
    }
}

interface NotificationJson {

}

@Getter
class NotificationWithoutMembersJson implements NotificationJson {
    private final String subject;
    private final String message;

    public NotificationWithoutMembersJson(Notification notification) {
        this.subject = notification.getSubject();
        this.message = notification.getMessage();
    }
}

@Getter
class NotificationWithMembersJson implements NotificationJson {
    private final String subject;
    private final String message;
    private final Collection<MemberIdJson> receivers;

    public NotificationWithMembersJson(Notification notification, Collection<MemberId> members) {
        this.subject = notification.getSubject();
        this.message = notification.getMessage();
        this.receivers = receiversFrom(members);
    }

    private Collection<MemberIdJson> receiversFrom(Collection<MemberId> members) {
        return members.stream()
                .map(MemberId::value)
                .map(MemberIdJson::new)
                .collect(Collectors.toList());
    }

    @Value
    static class MemberIdJson {
        String userId;
    }
}
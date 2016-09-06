package knbit.events.bc.common.infrastructure.mailnotifications.rest;

import knbit.events.bc.common.infrastructure.mailnotifications.Notification;
import knbit.events.bc.common.infrastructure.mailnotifications.NotificationBCClient;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

import static knbit.events.bc.common.infrastructure.mailnotifications.rest.NotificationJSONs.*;

/**
 * Created by novy on 06.12.15.
 */

@RequiredArgsConstructor
class RestNotificationBCClient implements NotificationBCClient {

    private final TokenProvider tokenProvider;
    private final NotificationEndpoints endpoints;
    private final RestTemplate restTemplate;

    @Override
    public void sendNotificationToAllMembers(Notification notification) {
        tokenProvider
                .provideToken()
                .ifPresent(token -> sendNotification(
                        token,
                        endpoints.getNotifyAllMembersEndpoint(),
                        new NotificationWithoutMembersJson(notification)
                ));
    }

    @Override
    public void sendNotificationTo(Collection<MemberId> members, Notification notification) {
        tokenProvider
                .provideToken()
                .ifPresent(token -> sendNotification(
                        token,
                        endpoints.getNotifyMembersEndpoint(),
                        new NotificationWithMembersJson(notification, members)
                ));
    }

    private void sendNotification(String token, String notificationUrl, NotificationJson notification) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set(tokenProvider.tokenHeaderName(), token);

        restTemplate.postForObject(notificationUrl, new HttpEntity<>(notification, headers), Void.class);
    }
}


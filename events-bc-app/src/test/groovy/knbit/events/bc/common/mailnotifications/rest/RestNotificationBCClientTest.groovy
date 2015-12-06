package knbit.events.bc.common.mailnotifications.rest

import knbit.events.bc.common.mailnotifications.Notification
import knbit.events.bc.common.mailnotifications.NotificationBCClient
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import net.minidev.json.JSONArray
import org.springframework.http.HttpMethod
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

/**
 * Created by novy on 06.12.15.
 */
class RestNotificationBCClientTest extends Specification {

    def NotificationBCConfiguration config
    def RestTemplate restTemplate
    def MockRestServiceServer mockRestServiceServer
    @Shared
    def NotificationBCClient objectUnderTest

    void setup() {
        config = new NotificationBCConfiguration("http://example.com/mail", "http://example.com/mail/all")
        restTemplate = new RestTemplate()
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate)
    }

    @Unroll
    def "should do nothing given no token"() {
        given:
        objectUnderTest = new RestNotificationBCClient({ -> Optional.empty() }, config, restTemplate)

        when:
        sendNotificationRequest()

        then:
        mockRestServiceServer.verify()

        where:
        sendNotificationRequest << [
                { -> objectUnderTest.sendNotificationToAllMembers(new Notification("subject", "msg")) },
                { ->
                    objectUnderTest.sendNotificationTo(
                            [MemberId.of("dummy member")],
                            new Notification("subject", "msg")
                    )
                }
        ]
    }

    def "when notifying all members, given valid token, it should include it as header and perform POST with proper payload"() {
        given:
        def validToken = "validToken"
        def notificationForAllMembers = new Notification("subject", "message")

        objectUnderTest = new RestNotificationBCClient({ -> Optional.of(validToken) }, config, restTemplate)

        mockRestServiceServer
                .expect(requestTo(config.notifyAllMembersEndpoint))
                .andExpect(header("knbit-aa-auth", validToken))
                .andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("subject").value(notificationForAllMembers.subject))
                .andExpect(jsonPath("message").value(notificationForAllMembers.message))
                .andRespond(withSuccess())

        when:
        objectUnderTest.sendNotificationToAllMembers(notificationForAllMembers)

        then:
        mockRestServiceServer.verify()
    }

    def "when notifying chosen members, given valid token, it should include it as header and perform POST with proper payload"() {
        given:
        def validToken = "validToken"
        def notification = new Notification("subject", "message")
        def members = [MemberId.of("id1"), MemberId.of("id2")]

        objectUnderTest = new RestNotificationBCClient({ -> Optional.of(validToken) }, config, restTemplate)

        def expectedMembers = new JSONArray()
        expectedMembers.addAll([userId: "id1"], [userId: "id2"])
        mockRestServiceServer
                .expect(requestTo(config.notifyMembersEndpoint))
                .andExpect(header("knbit-aa-auth", validToken))
                .andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("subject").value(notification.subject))
                .andExpect(jsonPath("message").value(notification.message))
                .andExpect(jsonPath("receivers").value(expectedMembers))
                .andRespond(withSuccess())

        when:
        objectUnderTest.sendNotificationTo(members, notification)

        then:
        mockRestServiceServer.verify()
    }
}

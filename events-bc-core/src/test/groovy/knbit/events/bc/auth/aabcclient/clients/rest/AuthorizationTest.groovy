package knbit.events.bc.auth.aabcclient.clients.rest

import knbit.events.bc.auth.Role
import knbit.events.bc.auth.aabcclient.authorization.AuthorizationResult
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus

/**
 * Created by novy on 25.07.15.
 */
class AuthorizationTest extends Specification {

    def RestAABCClient objectUnderTest
    def MockRestServiceServer mockRestServiceServer
    def authorizationUrl = "http://example.com/authorize"

    void setup() {
        def RestTemplate restTemplate = new RestTemplate()
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate)
        objectUnderTest = new RestAABCClient("authentication url", authorizationUrl, "tokenHeaderKey", restTemplate)
    }

    def "should authorize successfully on successful aa-bc response"() {
        given:
        mockRestServiceServer
                .expect(requestTo(authorizationUrl))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(responseCode))

        when:
        def AuthorizationResult authorizationResult = objectUnderTest.authorizeWith("valid token", Role.EVENT_MASTER)

        then:
        authorizationResult.wasSuccessful()
        authorizationResult.statusCode() == responseCode

        where:
        responseCode << HttpStatus.values().findAll { it -> it.is2xxSuccessful() }
    }

    def "should not authorize on 4xx aa-bc response"() {
        given:
        mockRestServiceServer
                .expect(requestTo(authorizationUrl))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(responseCode))

        when:
        def AuthorizationResult authorizationResult = objectUnderTest.authorizeWith("invalid token", Role.EVENT_MASTER)

        then:
        !authorizationResult.wasSuccessful()
        authorizationResult.statusCode() == responseCode

        where:
        responseCode << HttpStatus.values().findAll { it -> it.is4xxClientError() }
    }

    def "should not authorize on 5xx aa-bc response"() {
        given:
        mockRestServiceServer
                .expect(requestTo(authorizationUrl))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(responseCode))

        when:
        def AuthorizationResult authorizationResult = objectUnderTest.authorizeWith("invalid token", Role.EVENT_MASTER)

        then:
        !authorizationResult.wasSuccessful()
        authorizationResult.statusCode() == responseCode

        where:
        responseCode << HttpStatus.values().findAll { it -> it.is5xxServerError() }
    }
}

package knbit.events.bc.auth.aabcclient.clients.rest

import knbit.events.bc.auth.Role
import knbit.events.bc.auth.aabcclient.authorization.AuthorizationResult
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*
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

    def "should not authorize if RestTemplate throws any other RestClientException"() {
        given:
        def RestTemplate restTemplateMock = Mock(RestTemplate)
        restTemplateMock._ >> {
            throw new RestClientException("ex")
        }
        objectUnderTest = new RestAABCClient(
                "url doesnt matter",
                "url doesnt matter",
                "token doesnt matter",
                restTemplateMock)

        when:
        def authorizationResult = objectUnderTest.authorizeWith("token", Role.EVENT_MASTER)

        then:
        !authorizationResult.wasSuccessful()
    }

    def "should POST for authorization url with permission as payload and token in header"() {
        given:
        def tokenHeaderKey = "knbit-aa-auth"
        def expectedToken = "token"
        def restTemplate = new RestTemplate()
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate)
        objectUnderTest = new RestAABCClient(
                "authenticationEndpoint doesnt matter",
                authorizationUrl,
                tokenHeaderKey, restTemplate)

        mockRestServiceServer
                .expect(requestTo(authorizationUrl))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("knbit-aa-auth", expectedToken))
                .andExpect(jsonPath("permission").value(Role.EVENT_MASTER.name()))
                .andRespond(withStatus(HttpStatus.OK))

        when:
        objectUnderTest.authorizeWith(expectedToken, Role.EVENT_MASTER)

        then:
        mockRestServiceServer.verify()
    }
}

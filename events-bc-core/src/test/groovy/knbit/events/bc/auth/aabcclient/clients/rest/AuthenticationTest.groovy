package knbit.events.bc.auth.aabcclient.clients.rest

import knbit.events.bc.auth.aabcclient.authentication.AuthenticationResult
import org.hamcrest.CoreMatchers
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus

/**
 * Created by novy on 25.07.15.
 */
class AuthenticationTest extends Specification {

    def RestAABCClient objectUnderTest
    def MockRestServiceServer mockRestServiceServer
    def authenticationUrl = "http://example.com/authenticate"

    void setup() {
        def RestTemplate restTemplate = new RestTemplate()
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate)
        objectUnderTest = new RestAABCClient("login url", authenticationUrl, "authorization endpoint", "tokenHeaderKey", restTemplate)
    }

    def "should authenticate successfully on successful aa-bc response"() {
        given:
        mockRestServiceServer
                .expect(requestTo(authenticationUrl))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(responseCode))


        when:
        def AuthenticationResult authenticationResult = objectUnderTest.authenticateWith("valid token")

        then:
        authenticationResult.wasSuccessful()
        authenticationResult.statusCode() == responseCode

        where:
        responseCode << HttpStatus.values().findAll { it -> it.is2xxSuccessful() }
    }

    def "should not authenticate on 4xx aa-bc response"() {
        given:
        mockRestServiceServer
                .expect(requestTo(authenticationUrl))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(responseCode))

        when:
        def AuthenticationResult authenticationResult = objectUnderTest.authenticateWith("invalid token")

        then:
        !authenticationResult.wasSuccessful()
        authenticationResult.statusCode() == responseCode

        where:
        responseCode << HttpStatus.values().findAll { it -> it.is4xxClientError() }
    }

    def "should not authenticate on 5xx aa-bc response"() {
        given:
        mockRestServiceServer
                .expect(requestTo(authenticationUrl))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(responseCode))

        when:
        def AuthenticationResult authenticationResult = objectUnderTest.authenticateWith("invalid token")

        then:
        !authenticationResult.wasSuccessful()
        authenticationResult.statusCode() == responseCode

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
                "login url doesn't matter",
                "url doesnt matter",
                "url doesnt matter",
                "token doesnt matter",
                restTemplateMock)

        when:
        def authenticationResult = objectUnderTest.authenticateWith("token")

        then:
        !authenticationResult.wasSuccessful()
    }

    def "should POST for authentication url with token as payload"() {
        given:
        def expectedToken = "token"
        mockRestServiceServer
                .expect(requestTo(authenticationUrl))
                .andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("token").value(expectedToken))
                .andRespond(withStatus(HttpStatus.OK))

        when:
        objectUnderTest.authenticateWith(expectedToken)

        then:
        mockRestServiceServer.verify()
    }
}

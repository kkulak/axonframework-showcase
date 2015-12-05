package knbit.events.bc.auth.aabcclient.clients.rest

import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

/**
 * Created by novy on 05.12.15.
 */
class ObtainingTokenTest extends Specification {

    def RestAABCClient objectUnderTest
    def MockRestServiceServer mockRestServiceServer
    def loginUrl = "http://example.com/login"

    void setup() {
        def RestTemplate restTemplate = new RestTemplate()
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate)
        objectUnderTest = new RestAABCClient(loginUrl, "authentication endpoint", "authorization endpoint", "tokenHeaderKey", restTemplate)
    }

    def "should return empty token if got 401"() {
        given:
        mockRestServiceServer
                .expect(requestTo(loginUrl))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.UNAUTHORIZED))

        when:
        def emptyToken = objectUnderTest.obtainToken("invalidEmail", "invalid password")

        then:
        !emptyToken.isPresent()
    }

    def "given valid email and password, should return token from response"() {
        given:
        mockRestServiceServer
                .expect(requestTo(loginUrl))
                .andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("email").value("valid@email.com"))
                .andExpect(jsonPath("password").value("validPassword"))
                .andRespond(withSuccess('{"token": "token", "userId": "anId"}', MediaType.APPLICATION_JSON))

        when:
        def token = objectUnderTest.obtainToken("valid@email.com", "validPassword")

        then:
        token.get() == "token"
    }

    def "should return empty token given any other status code (#statusCode)"() {
        given:
        mockRestServiceServer
                .expect(requestTo(loginUrl))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(statusCode))

        when:
        def emptyToken = objectUnderTest.obtainToken("invalidEmail", "invalid password")

        then:
        !emptyToken.isPresent()

        where:
        statusCode << HttpStatus.values().findAll { ![HttpStatus.OK, HttpStatus.UNAUTHORIZED].contains(it) }
    }
}

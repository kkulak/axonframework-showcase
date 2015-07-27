package knbit.notification.bc.config.auth

import com.google.common.collect.Maps
import knbit.events.bc.auth.Role
import knbit.events.bc.auth.aabcclient.clients.AABCClient
import org.springframework.http.HttpStatus
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.web.socket.WebSocketHandler
import spock.lang.Specification

import static knbit.events.bc.auth.aabcclient.authorization.AuthorizationResult.FailureAuthorization
import static knbit.events.bc.auth.aabcclient.authorization.AuthorizationResult.SuccessfulAuthorization

/**
 * Created by novy on 26.07.15.
 */
class WebSocketSecurityHandshakeInterceptorTest extends Specification {

    def tokenKey = "tokenKey"
    def token = "token"
    def requiredRole = Role.EVENT_MASTER
    def AABCClient aabcClientMock
    def WebSocketSecurityHandshakeInterceptor objectUnderTest

    void setup() {
        aabcClientMock = Mock(AABCClient)
        objectUnderTest = new WebSocketSecurityHandshakeInterceptor(aabcClientMock, tokenKey, requiredRole)
    }

    def "should abort handshake if AABCClient doesn't authorize given role"() {
        given:
        aabcClientMock.authorizeWith(token, requiredRole) >> new FailureAuthorization(HttpStatus.UNAUTHORIZED)

        when:
        def interceptorResult = objectUnderTest.beforeHandshake(
                requestWithUrl("http://example.com/ws?${tokenKey}=${token}"),
                Mock(ServerHttpResponse),
                Mock(WebSocketHandler),
                Maps.newHashMap()
        )

        then:
        !interceptorResult
    }

    def "should set response code if AABCClient doesn't authorize given role"() {
        given:
        aabcClientMock.authorizeWith(token, requiredRole) >> new FailureAuthorization(HttpStatus.UNAUTHORIZED)

        when:
        def response = new ServletServerHttpResponse(new MockHttpServletResponse())
        objectUnderTest.beforeHandshake(
                requestWithUrl("http://example.com/ws?${tokenKey}=${token}"),
                response,
                Mock(WebSocketHandler),
                Maps.newHashMap()
        )

        then:
        response.getServletResponse().getStatus() == HttpStatus.UNAUTHORIZED.value()
    }

    def "should proceed with handshake if AABCClient authorizes given role"() {
        given:
        aabcClientMock.authorizeWith(token, requiredRole) >> new SuccessfulAuthorization(HttpStatus.OK, "refreshedToken")

        when:
        def interceptorResult = objectUnderTest.beforeHandshake(
                requestWithUrl("http://example.com/ws?${tokenKey}=${token}"),
                Mock(ServerHttpResponse),
                Mock(WebSocketHandler),
                Maps.newHashMap()
        )

        then:
        interceptorResult
    }

    def requestWithUrl(String url) {
        def request = Mock(ServerHttpRequest)
        request.getURI() >> new URI(url)
        request
    }
}

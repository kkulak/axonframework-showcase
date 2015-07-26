package knbit.events.bc.auth

import knbit.events.bc.auth.aabcclient.clients.AABCClient
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.method.HandlerMethod
import spock.lang.Specification

import static knbit.events.bc.auth.aabcclient.authentication.AuthenticationResult.FailureAuthentication
import static knbit.events.bc.auth.aabcclient.authentication.AuthenticationResult.SuccessfulAuthentication
import static knbit.events.bc.auth.aabcclient.authorization.AuthorizationResult.FailureAuthorization
import static knbit.events.bc.auth.aabcclient.authorization.AuthorizationResult.SuccessfulAuthorization

/**
 * Created by novy on 26.07.15.
 */
class SecurityInterceptorTest extends Specification {

    @RestController
    static class ExampleController {
        @RequestMapping(method = RequestMethod.GET)
        def unsecuredMethod() {
            "hello"
        }

        @Authenticated
        @RequestMapping(method = RequestMethod.POST)
        def authenticatedMethod() {}

        @Authorized(Role.EVENT_MASTER)
        @RequestMapping(method = RequestMethod.PUT)
        def authorizedMethod() {}
    }

    def "if not given HandlerMethod, should return true"() {
        when:
        def objectUnderTest = new SecurityInterceptor(
                Mock(AABCClient), "authHeaderKey"
        )

        then:
        objectUnderTest.preHandle(
                new MockHttpServletRequest(),
                new MockHttpServletResponse(),
                new Object()
        )
    }

    def "given unsecuredMethod method should return true"() {
        given:
        def exampleController = new ExampleController()
        def objectUnderTest = new SecurityInterceptor(
                Mock(AABCClient), "authKey"
        )

        when:
        def handlerMethod = new HandlerMethod(exampleController, "unsecuredMethod")
        def interceptorResult = objectUnderTest.preHandle(
                new MockHttpServletRequest(),
                new MockHttpServletResponse(),
                handlerMethod
        )

        then:
        interceptorResult
    }

    def "given unsecuredMethod method should not add any header"() {
        given:
        def exampleController = new ExampleController()
        def objectUnderTest = new SecurityInterceptor(
                Mock(AABCClient), "authKey"
        )

        when:
        def handlerMethod = new HandlerMethod(exampleController, "unsecuredMethod")
        def response = new MockHttpServletResponse()
        objectUnderTest.preHandle(
                new MockHttpServletRequest(),
                response,
                handlerMethod
        )

        then:
        response.getHeader("authKey") == null
    }

    def "given authenticated method, should return true if AABCClient authenticates token"() {
        given:
        def AABCClient aabcClientMock = Mock(AABCClient)
        def validToken = "validToken"
        def authKey = "authKey"
        aabcClientMock.authenticateWith(validToken) >> new SuccessfulAuthentication(HttpStatus.OK, "refreshedToken")
        def exampleController = new ExampleController()
        def objectUnderTest = new SecurityInterceptor(aabcClientMock, authKey)

        when:
        def handlerMethod = new HandlerMethod(exampleController, "authenticatedMethod")
        def request = new MockHttpServletRequest()
        request.addHeader(authKey, validToken)
        def interceptorResult = objectUnderTest.preHandle(
                request,
                new MockHttpServletResponse(),
                handlerMethod
        )

        then:
        interceptorResult

    }

    def "given authenticated method, should set refreshed token if AABCClient authenticates token"() {
        given:
        def AABCClient aabcClientMock = Mock(AABCClient)
        def validToken = "validToken"
        def authKey = "authKey"
        aabcClientMock.authenticateWith(validToken) >> new SuccessfulAuthentication(HttpStatus.OK, "refreshedToken")
        def exampleController = new ExampleController()
        def objectUnderTest = new SecurityInterceptor(aabcClientMock, authKey)

        when:
        def handlerMethod = new HandlerMethod(exampleController, "authenticatedMethod")
        def request = new MockHttpServletRequest()
        request.addHeader(authKey, validToken)
        def response = new MockHttpServletResponse()
        objectUnderTest.preHandle(
                request,
                response,
                handlerMethod
        )

        then:
        response.getHeader(authKey) == "refreshedToken"
    }

    def "given authenticated method, should return false if AABCClient doesn't authenticate token"() {
        given:
        def AABCClient aabcClientMock = Mock(AABCClient)
        def invalidToken = "invalidKey"
        def authKey = "authKey"
        aabcClientMock.authenticateWith(invalidToken) >> new FailureAuthentication(HttpStatus.FORBIDDEN)
        def exampleController = new ExampleController()
        def objectUnderTest = new SecurityInterceptor(aabcClientMock, authKey)

        when:
        def handlerMethod = new HandlerMethod(exampleController, "authenticatedMethod")
        def request = new MockHttpServletRequest()
        request.addHeader(authKey, invalidToken)
        def interceptorResult = objectUnderTest.preHandle(
                request,
                new MockHttpServletResponse(),
                handlerMethod
        )

        then:
        !interceptorResult
    }

    def "given authenticated method, should set status code if AABCClient doesn't authenticate token"() {
        given:
        def AABCClient aabcClientMock = Mock(AABCClient)
        def invalidToken = "invalidKey"
        def authKey = "authKey"
        aabcClientMock.authenticateWith(invalidToken) >> new FailureAuthentication(HttpStatus.FORBIDDEN)
        def exampleController = new ExampleController()
        def objectUnderTest = new SecurityInterceptor(aabcClientMock, authKey)

        when:
        def handlerMethod = new HandlerMethod(exampleController, "authenticatedMethod")
        def request = new MockHttpServletRequest()
        request.addHeader(authKey, invalidToken)
        def response = new MockHttpServletResponse()
        objectUnderTest.preHandle(
                request,
                response,
                handlerMethod
        )

        then:
        HttpStatus.valueOf(response.getStatus()) == HttpStatus.FORBIDDEN
    }

    def "given authorized method, should return true if AABCClient authorizes role"() {
        given:
        def AABCClient aabcClientMock = Mock(AABCClient)
        def validToken = "validToken"
        def role = Role.EVENT_MASTER
        def authKey = "authKey"
        aabcClientMock.authorizeWith(validToken, role) >> new SuccessfulAuthorization(HttpStatus.OK, "refreshedToken")
        def exampleController = new ExampleController()
        def objectUnderTest = new SecurityInterceptor(aabcClientMock, authKey)

        when:
        def handlerMethod = new HandlerMethod(exampleController, "authorizedMethod")
        def request = new MockHttpServletRequest()
        request.addHeader(authKey, validToken)
        def interceptorResult = objectUnderTest.preHandle(
                request,
                new MockHttpServletResponse(),
                handlerMethod
        )

        then:
        interceptorResult

    }

    def "given authorized method, should set refreshed token if AABCClient authorizes role"() {
        given:
        def AABCClient aabcClientMock = Mock(AABCClient)
        def validToken = "validToken"
        def role = Role.EVENT_MASTER
        def authKey = "authKey"
        aabcClientMock.authorizeWith(validToken, role) >> new SuccessfulAuthorization(HttpStatus.OK, "refreshedToken")
        def exampleController = new ExampleController()
        def objectUnderTest = new SecurityInterceptor(aabcClientMock, authKey)

        when:
        def handlerMethod = new HandlerMethod(exampleController, "authorizedMethod")
        def request = new MockHttpServletRequest()
        request.addHeader(authKey, validToken)
        def response = new MockHttpServletResponse()
        objectUnderTest.preHandle(
                request,
                response,
                handlerMethod
        )

        then:
        response.getHeader(authKey) == "refreshedToken"
    }

    def "given authorized method, should return false if AABCClient doesn't authorize role"() {
        given:
        def AABCClient aabcClientMock = Mock(AABCClient)
        def invalidToken = "invalidToken"
        def role = Role.EVENT_MASTER
        def authKey = "authKey"
        aabcClientMock.authorizeWith(invalidToken, role) >> new FailureAuthorization(HttpStatus.UNAUTHORIZED)
        def exampleController = new ExampleController()
        def objectUnderTest = new SecurityInterceptor(aabcClientMock, authKey)

        when:
        def handlerMethod = new HandlerMethod(exampleController, "authorizedMethod")
        def request = new MockHttpServletRequest()
        request.addHeader(authKey, invalidToken)
        def interceptorResult = objectUnderTest.preHandle(
                request,
                new MockHttpServletResponse(),
                handlerMethod
        )

        then:
        !interceptorResult
    }

    def "given authorized method, should set status code if AABCClient doesn't authorize role"() {
        given:
        def AABCClient aabcClientMock = Mock(AABCClient)
        def invalidToken = "invalidToken"
        def role = Role.EVENT_MASTER
        def authKey = "authKey"
        aabcClientMock.authorizeWith(invalidToken, role) >> new FailureAuthorization(HttpStatus.UNAUTHORIZED)
        def exampleController = new ExampleController()
        def objectUnderTest = new SecurityInterceptor(aabcClientMock, authKey)

        when:
        def handlerMethod = new HandlerMethod(exampleController, "authorizedMethod")
        def request = new MockHttpServletRequest()
        request.addHeader(authKey, invalidToken)
        def response = new MockHttpServletResponse()
        objectUnderTest.preHandle(
                request,
                response,
                handlerMethod
        )

        then:
        HttpStatus.valueOf(response.getStatus()) == HttpStatus.UNAUTHORIZED
    }


}

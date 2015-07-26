package knbit.events.bc.auth;

import com.google.common.collect.Iterables;
import knbit.events.bc.auth.aabcclient.AABCResult;
import knbit.events.bc.auth.aabcclient.clients.AABCClient;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by novy on 25.07.15.
 */

public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private final AABCClient aabcClient;
    private final String authHeaderKey;

    public SecurityInterceptor(AABCClient aabcClient, String authHeaderKey) {
        this.aabcClient = aabcClient;
        this.authHeaderKey = authHeaderKey;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        final HandlerMethod methodHandler = (HandlerMethod) handler;
        final String authToken = request.getHeader(authHeaderKey);

        final Optional<AABCResult> possibleAuthenticationResult = tryToAuthenticateWith(authToken, methodHandler);
        final Optional<AABCResult> possibleAuthorizationResult = tryToAuthorizeWith(authToken, methodHandler);

        Optional<AABCResult> authenticationOrAuthorizationResult =
                findAuthenticationOrAuthorizationResult(possibleAuthenticationResult, possibleAuthorizationResult);

        authenticationOrAuthorizationResult.ifPresent(aabcResult -> modifyResponseAccordingTo(aabcResult, response));

        return authenticationOrAuthorizationResult
                .map(AABCResult::wasSuccessful)
                .orElse(Boolean.TRUE);

    }

    private void modifyResponseAccordingTo(AABCResult aabcResult, HttpServletResponse response) {
        if (aabcResult.wasSuccessful()) {
            response.setHeader(authHeaderKey, aabcResult.refreshedToken());
        } else {
            response.setStatus(aabcResult.statusCode().value());
        }
    }

    private Optional<AABCResult> findAuthenticationOrAuthorizationResult(Optional<AABCResult> possibleAuthenticationResult,
                                                                         Optional<AABCResult> possibleAuthorizationResult) {

        final Collection<Optional<AABCResult>> bothAsList =
                Arrays.asList(possibleAuthorizationResult, possibleAuthenticationResult);

        return Iterables
                .tryFind(bothAsList, Optional::isPresent)
                .or(Optional::empty);
    }

    private Optional<AABCResult> tryToAuthorizeWith(String authToken, HandlerMethod methodHandler) {
        return Optional.of(Authorized.class)
                .map(methodHandler::getMethodAnnotation)
                .map(Authorized::value)
                .map(role -> aabcClient.authorizeWith(authToken, role));
    }

    private Optional<AABCResult> tryToAuthenticateWith(String authToken, HandlerMethod methodHandler) {
        return Optional.of(Authenticated.class)
                .map(methodHandler::getMethodAnnotation)
                .map(authenticationAnnotation -> aabcClient.authenticateWith(authToken));
    }
}

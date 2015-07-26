package knbit.events.bc.auth;

import knbit.events.bc.auth.aabcclient.AABCResult;
import knbit.events.bc.auth.aabcclient.clients.AABCClient;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.stream.Stream;

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

        if (!possibleAuthenticationResult.isPresent() && !possibleAuthorizationResult.isPresent()) {
            return true;
        }

        if (possibleAuthenticationResult.isPresent() && !possibleAuthorizationResult.isPresent()) {
            final AABCResult aabcResult = possibleAuthenticationResult.get();
            if (aabcResult.wasSuccessful()) {
                response.setHeader(authHeaderKey, aabcResult.refreshedToken());
                return true;
            } else {
                response.setStatus(aabcResult.statusCode().value());
                return false;
            }
        }

        if (possibleAuthorizationResult.isPresent()) {
            final AABCResult aabcResult = possibleAuthorizationResult.get();
            if (aabcResult.wasSuccessful()) {
                response.setHeader(authHeaderKey, aabcResult.refreshedToken());
                return true;
            } else {
                response.setStatus(aabcResult.statusCode().value());
                return false;
            }
        }


//
//        Stream.of(possibleAuthenticationResult, possibleAuthorizationResult)
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .filter(AABCResult::wasSuccessful)
//                .findFirst()
//                .ifPresent(aabcResult -> response.addHeader(authHeaderKey, aabcResult.refreshedToken()));


        return false;

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

//    private Optional<AABCResult> findFirstMatching()
}

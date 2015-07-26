package knbit.notification.bc.config.auth;

import com.aol.cyclops.trycatch.Try;
import knbit.events.bc.auth.Role;
import knbit.events.bc.auth.aabcclient.authorization.AuthorizationResult;
import knbit.events.bc.auth.aabcclient.clients.AABCClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

/**
 * Created by novy on 26.07.15.
 */
@RequiredArgsConstructor
public class WebSocketSecurityHandshakeInterceptor implements HandshakeInterceptor {

    private final AABCClient aabcClient;
    private final String authHeaderKey;
    private final Role requiredRole;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        final Optional<AuthorizationResult> authorizationResult =
                extractTokenFrom(request).map(this::tryAuthorizeWith);

        authorizationResult.ifPresent(result -> setReturnCodeOnFailure(result, response));

        return authorizationResult
                .map(AuthorizationResult::wasSuccessful)
                .orElse(Boolean.FALSE);
    }

    private Optional<String> extractTokenFrom(ServerHttpRequest request) {
        final URI uri = request.getURI();
        return Try
                .withCatch(uri::toURL, MalformedURLException.class)
                .toOptional()
                .map(URL::getQuery)
                .map(queryString -> queryString.split("="))
                .map(queryChunks -> queryChunks[1]);
    }

    private AuthorizationResult tryAuthorizeWith(String token) {
        return aabcClient.authorizeWith(token, requiredRole);
    }

    private void setReturnCodeOnFailure(AuthorizationResult authorizationResult, ServerHttpResponse response) {
        if (!authorizationResult.wasSuccessful()) {
            response.setStatusCode(authorizationResult.statusCode());
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}

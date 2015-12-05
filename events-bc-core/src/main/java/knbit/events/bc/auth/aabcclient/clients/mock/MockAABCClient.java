package knbit.events.bc.auth.aabcclient.clients.mock;

import knbit.events.bc.auth.Role;
import knbit.events.bc.auth.aabcclient.clients.AABCClient;
import knbit.events.bc.auth.aabcclient.authentication.AuthenticationResult;
import knbit.events.bc.auth.aabcclient.authentication.AuthenticationResult.SuccessfulAuthentication;
import knbit.events.bc.auth.aabcclient.authorization.AuthorizationResult;
import knbit.events.bc.auth.aabcclient.authorization.AuthorizationResult.SuccessfulAuthorization;
import org.springframework.http.HttpStatus;

import java.util.Optional;

/**
 * Created by novy on 25.07.15.
 */
public class MockAABCClient implements AABCClient {

    @Override
    public Optional<String> obtainToken(String email, String password) {
        return Optional.of("fake token");
    }

    @Override
    public AuthenticationResult authenticateWith(String token) {
        return new SuccessfulAuthentication(HttpStatus.NO_CONTENT, token);
    }

    @Override
    public AuthorizationResult authorizeWith(String token, Role role) {
        return new SuccessfulAuthorization(HttpStatus.OK, token);
    }
}

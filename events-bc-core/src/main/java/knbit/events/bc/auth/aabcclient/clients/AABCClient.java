package knbit.events.bc.auth.aabcclient.clients;

import knbit.events.bc.auth.Role;
import knbit.events.bc.auth.aabcclient.authentication.AuthenticationResult;
import knbit.events.bc.auth.aabcclient.authorization.AuthorizationResult;

import java.util.Optional;

/**
 * Created by novy on 25.07.15.
 */
public interface AABCClient {

    Optional<String> obtainToken(String serviceId, String password);

    AuthenticationResult authenticateWith(String token);

    AuthorizationResult authorizeWith(String token, Role role);

}

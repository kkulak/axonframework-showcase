package knbit.events.bc.auth.aabcclient.clients;

import knbit.events.bc.auth.Role;
import knbit.events.bc.auth.aabcclient.authentication.AuthenticationResult;
import knbit.events.bc.auth.aabcclient.authorization.AuthorizationResult;

/**
 * Created by novy on 25.07.15.
 */
public interface AABCClient {

    AuthenticationResult authenticateWith(String token);

    AuthorizationResult authorizeWith(String token, Role role);

}

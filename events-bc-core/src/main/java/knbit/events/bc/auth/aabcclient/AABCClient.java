package knbit.events.bc.auth.aabcclient;

import knbit.events.bc.auth.Role;

/**
 * Created by novy on 25.07.15.
 */
public interface AABCClient {

    boolean authenticated(String token);

    boolean hasRole(String token, Role role);
}

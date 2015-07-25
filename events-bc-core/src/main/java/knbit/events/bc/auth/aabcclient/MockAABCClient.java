package knbit.events.bc.auth.aabcclient;

import knbit.events.bc.auth.Role;

/**
 * Created by novy on 25.07.15.
 */
public class MockAABCClient implements AABCClient {
    @Override
    public boolean authenticated(String token) {
        return true;
    }

    @Override
    public boolean hasRole(String token, Role role) {
        return true;
    }
}

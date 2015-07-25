package knbit.events.bc.auth.aabcclient;

import knbit.events.bc.auth.Role;
import org.springframework.web.client.RestTemplate;

/**
 * Created by novy on 25.07.15.
 */
public class RestAABCClient implements AABCClient {

    private final String authenticationEndpoint;
    private final String authorizationEndpoint;
    private final String tokenHeaderKey;
    private final RestTemplate restTemplate;

    public RestAABCClient(String authenticationEndpoint, String authorizationEndpoint, String tokenHeaderKey, RestTemplate restTemplate) {
        this.authenticationEndpoint = authenticationEndpoint;
        this.authorizationEndpoint = authorizationEndpoint;
        this.tokenHeaderKey = tokenHeaderKey;
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean authenticated(String token) {
        return false;
    }

    @Override
    public boolean hasRole(String token, Role role) {
        return false;
    }
}

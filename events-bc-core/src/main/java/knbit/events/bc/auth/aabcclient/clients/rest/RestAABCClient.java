package knbit.events.bc.auth.aabcclient.clients.rest;

import knbit.events.bc.auth.aabcclient.clients.AABCClient;
import lombok.experimental.Delegate;
import org.springframework.web.client.RestTemplate;

/**
 * Created by novy on 25.07.15.
 */
public class RestAABCClient implements AABCClient {

    @Delegate
    private final AuthenticationClient authenticationClient;
    @Delegate
    private final AuthorizationClient authorizationClient;

    public RestAABCClient(String authenticationEndpoint, String authorizationEndpoint, String tokenHeaderKey, RestTemplate restTemplate) {
        this.authenticationClient = new AuthenticationClient(authenticationEndpoint, restTemplate);
        this.authorizationClient = new AuthorizationClient(authorizationEndpoint, tokenHeaderKey);
    }
}

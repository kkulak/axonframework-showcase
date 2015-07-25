package knbit.events.bc.auth.aabcclient;

import knbit.events.bc.auth.Role;
import lombok.Value;
import org.springframework.http.*;
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
        final ResponseEntity<TokenDto> response =
                restTemplate.postForEntity(authenticationEndpoint, new TokenDto(token), TokenDto.class);

        return response.getStatusCode() == HttpStatus.NO_CONTENT;
    }

    @Override
    public boolean hasRole(String token, Role role) {
        final HttpEntity<PermissionDto> request = createRequestFor(token, role);
        final ResponseEntity<PermissionDto> response =
                restTemplate.exchange(authorizationEndpoint, HttpMethod.POST, request, PermissionDto.class);

        return response.getStatusCode() == HttpStatus.OK;
    }

    private HttpEntity<PermissionDto> createRequestFor(String token, Role role) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(tokenHeaderKey, token);
        return new HttpEntity<>(new PermissionDto(role.name()), httpHeaders);
    }

    @Value
    private static class TokenDto {
        private final String token;
    }

    @Value
    private static class PermissionDto {
        private final String permission;
    }
}

package knbit.events.bc.auth.aabcclient.clients.rest;

import com.aol.cyclops.trycatch.Try;
import knbit.events.bc.auth.Role;
import knbit.events.bc.auth.aabcclient.authorization.AuthorizationResult;
import knbit.events.bc.auth.aabcclient.authorization.AuthorizationResult.FailureAuthorization;
import knbit.events.bc.auth.aabcclient.authorization.AuthorizationResult.SuccessfulAuthorization;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by novy on 25.07.15.
 */
class AuthorizationClient {

    private final String authorizationEndpoint;
    private final String tokenHeaderKey;
    private final RestTemplate restTemplate;

    public AuthorizationClient(String authorizationEndpoint, String tokenHeaderKey, RestTemplate restTemplate) {
        this.authorizationEndpoint = authorizationEndpoint;
        this.tokenHeaderKey = tokenHeaderKey;
        this.restTemplate = restTemplate;
    }

    public AuthorizationResult authorizeWith(String token, Role role) {
        return Try
                .withCatch(
                        () -> tryAuthorizeWith(token, role),
                        HttpStatusCodeException.class, RestClientException.class
                )
                .map(response -> fromServerResponse(response, token))
                .recoverFor(HttpStatusCodeException.class, ex -> handle((HttpStatusCodeException) ex))
                .recoverFor(RestClientException.class, this::handle)
                .get();
    }

    private ResponseEntity<AuthorizationResponseDto> tryAuthorizeWith(String token, Role role) {
        final HttpEntity<PermissionDto> request = createRequestFor(token, role);
        return restTemplate.exchange(authorizationEndpoint, HttpMethod.POST, request, AuthorizationResponseDto.class);
    }

    private AuthorizationResult fromServerResponse(ResponseEntity<AuthorizationResponseDto> response, String token) {
        final HttpStatus statusCode = response.getStatusCode();
        return statusCode.is2xxSuccessful() ?
                new SuccessfulAuthorization(statusCode, token) : new FailureAuthorization(statusCode);
    }

    private AuthorizationResult handle(HttpStatusCodeException exception) {
        return new FailureAuthorization(exception.getStatusCode());
    }

    private AuthorizationResult handle(RestClientException exception) {
        return new FailureAuthorization(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private HttpEntity<PermissionDto> createRequestFor(String token, Role role) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(tokenHeaderKey, token);
        return new HttpEntity<>(new PermissionDto(role.name()), httpHeaders);
    }

    @Value
    private static class PermissionDto {
        private final String permission;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class AuthorizationResponseDto {
        private String userId;
    }
}

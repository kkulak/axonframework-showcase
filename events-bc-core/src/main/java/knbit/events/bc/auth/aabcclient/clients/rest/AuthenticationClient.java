package knbit.events.bc.auth.aabcclient.clients.rest;

import com.aol.cyclops.trycatch.Try;
import knbit.events.bc.auth.aabcclient.authentication.AuthenticationResult;
import knbit.events.bc.auth.aabcclient.authentication.AuthenticationResult.FailureAuthentication;
import knbit.events.bc.auth.aabcclient.authentication.AuthenticationResult.SuccessfulAuthentication;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by novy on 25.07.15.
 */
class AuthenticationClient {

    private final String authenticationEndpoint;
    private final RestTemplate restTemplate;

    public AuthenticationClient(String authenticationEndpoint, RestTemplate restTemplate) {
        this.authenticationEndpoint = authenticationEndpoint;
        this.restTemplate = restTemplate;
    }

    public AuthenticationResult authenticateWith(String token) {
        return Try
                .withCatch(
                        () -> tryAuthenticateWith(token),
                        HttpStatusCodeException.class, RestClientException.class
                )
                .map(response -> fromServerResponse(response, token))
                .recoverFor(HttpStatusCodeException.class, ex -> handle((HttpStatusCodeException) ex))
                .recoverFor(RestClientException.class, this::handle)
                .get();
    }

    private ResponseEntity<TokenDto> tryAuthenticateWith(String token) {
        return restTemplate.postForEntity(authenticationEndpoint, new TokenDto(token), TokenDto.class);
    }

    private AuthenticationResult fromServerResponse(ResponseEntity<TokenDto> response, String token) {
        final HttpStatus statusCode = response.getStatusCode();
        return statusCode.is2xxSuccessful() ?
                new SuccessfulAuthentication(statusCode, token) : new FailureAuthentication(statusCode);
    }

    private AuthenticationResult handle(HttpStatusCodeException exception) {
        return new FailureAuthentication(exception.getStatusCode());
    }

    private AuthenticationResult handle(RestClientException exception) {
        return new FailureAuthentication(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Value
    private static class TokenDto {
        private final String token;
    }

}

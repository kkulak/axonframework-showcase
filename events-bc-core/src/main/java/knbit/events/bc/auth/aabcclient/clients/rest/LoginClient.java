package knbit.events.bc.auth.aabcclient.clients.rest;

import com.lambdista.util.Try;
import lombok.Data;
import lombok.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Created by novy on 05.12.15.
 */

@Value
class LoginClient {

    String tokenObtainingEndpoint;
    RestTemplate restTemplate;

    public Optional<String> obtainToken(String email, String password) {
        return tryToRetrieveToken(email, password)
                .map(HttpEntity::getBody)
                .map(LoginResponse::getToken)
                .toOptional();
    }

    private Try<ResponseEntity<LoginResponse>> tryToRetrieveToken(String email, String password) {
        return Try.apply(() -> restTemplate.postForEntity(
                tokenObtainingEndpoint,
                new LoginRequest(email, password),
                LoginResponse.class
        ));
    }
}

@Value
class LoginRequest {
    String email;
    String password;
}

@Data
class LoginResponse {
    String token;
    String userId;
}

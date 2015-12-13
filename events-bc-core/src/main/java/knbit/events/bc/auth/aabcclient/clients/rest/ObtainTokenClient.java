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
class ObtainTokenClient {

    String tokenObtainingEndpoint;
    RestTemplate restTemplate;

    public Optional<String> obtainToken(String email, String password) {
        return tryToRetrieveToken(email, password)
                .map(HttpEntity::getBody)
                .map(ObtainTokenResponse::getToken)
                .toOptional();
    }

    private Try<ResponseEntity<ObtainTokenResponse>> tryToRetrieveToken(String email, String password) {
        return Try.apply(() -> restTemplate.postForEntity(
                tokenObtainingEndpoint,
                new ObtainTokenRequest(email, password),
                ObtainTokenResponse.class
        ));
    }
}

@Value
class ObtainTokenRequest {
    String userId;
    String password;
}

@Data
class ObtainTokenResponse {
    String token;
}

package knbit.events.bc.auth.aabcclient.authentication;

import lombok.Value;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

/**
 * Created by novy on 25.07.15.
 */
public interface AuthenticationResult {

    boolean wasSuccessful();

    HttpStatus statusCode();

    String refreshedToken();

    @Value
    @Accessors(fluent = true)
    class SuccessfulAuthentication implements AuthenticationResult {

        private final HttpStatus statusCode;
        private final String refreshedToken;

        @Override
        public boolean wasSuccessful() {
            return true;
        }
    }

    @Value
    @Accessors(fluent = true)
    class FailureAuthentication implements AuthenticationResult {

        private final HttpStatus statusCode;

        @Override
        public boolean wasSuccessful() {
            return false;
        }

        @Override
        public String refreshedToken() {
            throw new IllegalStateException();
        }
    }
}

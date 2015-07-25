package knbit.events.bc.auth.aabcclient.authorization;

import lombok.Value;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

/**
 * Created by novy on 25.07.15.
 */
public interface AuthorizationResult {

    boolean wasSuccessful();

    HttpStatus statusCode();

    String refreshedToken();

    @Value
    @Accessors(fluent = true)
    class SuccessfulAuthorization implements AuthorizationResult {

        private final HttpStatus statusCode;
        private final String refreshedToken;

        @Override
        public boolean wasSuccessful() {
            return true;
        }
    }

    @Value
    @Accessors(fluent = true)
    class FailureAuthorization implements AuthorizationResult {

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

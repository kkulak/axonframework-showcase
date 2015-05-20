package knbit.events.bc.common.domain.exceptions;

/**
 * Created by novy on 06.05.15.
 */

public class DomainException extends RuntimeException {

    public DomainException() {
    }

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}

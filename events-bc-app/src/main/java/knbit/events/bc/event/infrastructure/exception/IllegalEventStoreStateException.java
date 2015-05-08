package knbit.events.bc.event.infrastructure.exception;

public class IllegalEventStoreStateException extends RuntimeException {

    public IllegalEventStoreStateException(String message) {
        super(message);
    }

}

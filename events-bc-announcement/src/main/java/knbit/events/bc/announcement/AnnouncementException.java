package knbit.events.bc.announcement;

/**
 * Created by novy on 03.04.15.
 */
public class AnnouncementException extends RuntimeException {

    public AnnouncementException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnnouncementException(String message) {
        super(message);
    }
}

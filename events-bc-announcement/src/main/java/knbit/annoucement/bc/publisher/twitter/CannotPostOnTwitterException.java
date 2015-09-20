package knbit.annoucement.bc.publisher.twitter;

import knbit.annoucement.bc.publisher.AnnouncementException;

/**
 * Created by novy on 03.04.15.
 */
public class CannotPostOnTwitterException extends AnnouncementException {

    private static final String ERROR_MESSAGE = "Cannot post on Twitter!";

    public CannotPostOnTwitterException(Throwable cause) {
        super(ERROR_MESSAGE, cause);
    }
}

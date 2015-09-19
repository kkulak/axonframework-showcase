package knbit.events.bc.announcement.googlegroup.publisher;

import knbit.events.bc.announcement.AnnouncementException;

/**
 * Created by novy on 03.04.15.
 */
public class CannotPostOnGoogleGroupException extends AnnouncementException {

    private static final String ERROR_MESSAGE = "Cannot post announcement on google group!";

    public CannotPostOnGoogleGroupException(Throwable cause) {
        super(ERROR_MESSAGE, cause);
    }
}

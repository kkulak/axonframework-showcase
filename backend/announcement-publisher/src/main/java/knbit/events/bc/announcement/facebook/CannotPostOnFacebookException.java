package knbit.events.bc.announcement.facebook;

import knbit.events.bc.announcement.AnnouncementException;

/**
 * Created by novy on 03.04.15.
 */
public class CannotPostOnFacebookException extends AnnouncementException {

    private static final String ERROR_MESSAGE = "Cannot post on facebook!";

    public CannotPostOnFacebookException() {
        super(ERROR_MESSAGE);
    }
}

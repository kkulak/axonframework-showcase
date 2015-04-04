package knbit.events.bc.announcement.web;

import knbit.events.bc.announcement.AnnouncementException;

/**
 * Created by novy on 03.04.15.
 */
class SomethingWentWrongException extends AnnouncementException {

    public static final String ERROR_MESSAGE = "Something went really wrong!";

    public SomethingWentWrongException() {
        super(ERROR_MESSAGE);
    }
}

package knbit.events.bc.announcement.coordinator;

import knbit.events.bc.announcement.AnnouncementException;

/**
 * Created by novy on 02.04.15.
 */
public class NoPublisherSpecifiedException extends AnnouncementException {

    private static final String ERROR_MESSAGE = "No publisher specified!";

    public NoPublisherSpecifiedException() {
        super(ERROR_MESSAGE);
    }
}

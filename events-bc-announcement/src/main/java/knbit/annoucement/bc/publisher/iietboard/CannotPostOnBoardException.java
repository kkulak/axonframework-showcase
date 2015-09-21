package knbit.annoucement.bc.publisher.iietboard;

import knbit.annoucement.bc.publisher.AnnouncementException;

/**
 * Created by novy on 05.04.15.
 */
public class CannotPostOnBoardException extends AnnouncementException {

    private static final String ERROR_MESSAGE = "Cannot post on forum.iiet.pl!";

    public CannotPostOnBoardException(Throwable cause) {
        super(ERROR_MESSAGE, cause);
    }
}
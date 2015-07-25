package knbit.events.bc.interest.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.domain.valueobjects.Attendee;

/**
 * Created by novy on 31.05.15.
 */
public class NotVotedUpException extends DomainException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Attendee %s should vote up on event %s before!";

    public NotVotedUpException(EventId eventId, Attendee attendee) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, eventId, attendee));
    }
}

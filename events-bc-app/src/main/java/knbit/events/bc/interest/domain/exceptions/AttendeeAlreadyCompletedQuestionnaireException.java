package knbit.events.bc.interest.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.domain.valueobjects.Attendee;

/**
 * Created by novy on 31.05.15.
 */
public class AttendeeAlreadyCompletedQuestionnaireException extends DomainException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Attendee %s already completed questionnaire for event %s before!";

    public AttendeeAlreadyCompletedQuestionnaireException(EventId eventId, Attendee attendee) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, eventId, attendee));
    }

}

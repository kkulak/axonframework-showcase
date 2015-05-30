package knbit.events.bc.interest.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;

/**
 * Created by novy on 28.05.15.
 */
public class SurveyAlreadyVotedException extends DomainException {

    private static final String ERROR_MESSAGE_TEMPLATE =
            "Survey for event with id %s already voted by attendee %s";

    public SurveyAlreadyVotedException(EventId eventId, Attendee attendee) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, eventId, attendee));
    }
}

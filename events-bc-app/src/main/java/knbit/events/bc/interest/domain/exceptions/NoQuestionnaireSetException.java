package knbit.events.bc.interest.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.common.domain.valueobjects.EventId;

/**
 * Created by novy on 31.05.15.
 */
public class NoQuestionnaireSetException extends DomainException {

    private static final String ERROR_MESSAGE_TEMPLATE = "No questionnaire set for event with id %s!";

    public NoQuestionnaireSetException(EventId eventId) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, eventId));
    }
}

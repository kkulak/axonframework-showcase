package knbit.events.bc.interest.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.common.domain.valueobjects.EventId;

/**
 * Created by novy on 31.05.15.
 */
public class AlreadyHasQuestionnaireException extends DomainException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Event %s already has questionnaire assigned!";

    public AlreadyHasQuestionnaireException(EventId eventId) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, eventId));
    }
}

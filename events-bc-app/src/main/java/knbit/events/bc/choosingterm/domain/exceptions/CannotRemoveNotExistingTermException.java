package knbit.events.bc.choosingterm.domain.exceptions;

import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.common.domain.valueobjects.EventId;

/**
 * Created by novy on 19.08.15.
 */
public class CannotRemoveNotExistingTermException extends RuntimeException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Event with id %s doesn't have term %s";

    public CannotRemoveNotExistingTermException(EventId eventId, TermId termId) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, eventId, termId));
    }
}

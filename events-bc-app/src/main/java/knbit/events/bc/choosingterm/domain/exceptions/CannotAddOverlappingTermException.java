package knbit.events.bc.choosingterm.domain.exceptions;

import knbit.events.bc.choosingterm.domain.valuobjects.Term;
import knbit.events.bc.common.domain.valueobjects.EventId;

/**
 * Created by novy on 19.08.15.
 */
public class CannotAddOverlappingTermException extends RuntimeException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Cannot add overlapping term (%s) to event with id %s";

    public CannotAddOverlappingTermException(EventId eventId, Term term) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, term, eventId));
    }
}

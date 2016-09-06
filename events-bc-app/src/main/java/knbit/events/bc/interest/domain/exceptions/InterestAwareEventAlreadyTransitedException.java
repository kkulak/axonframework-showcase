package knbit.events.bc.interest.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.common.domain.valueobjects.EventId;

/**
 * Created by novy on 16.08.15.
 */
public class InterestAwareEventAlreadyTransitedException extends DomainException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Event with id %s already transited to UnderChoosingTermEvent!";

    public InterestAwareEventAlreadyTransitedException(EventId eventId) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, eventId));
    }
}

package knbit.events.bc.eventproposal.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;

/**
 * Created by novy on 06.05.15.
 */
public class CannotRejectAcceptedProposalException extends DomainException {

    private static final String MESSAGE_PATTERN = "Cannot reject already accepted proposal (id = %s)";

    public CannotRejectAcceptedProposalException(String eventProposalId) {
        super(String.format(MESSAGE_PATTERN, eventProposalId));
    }
}

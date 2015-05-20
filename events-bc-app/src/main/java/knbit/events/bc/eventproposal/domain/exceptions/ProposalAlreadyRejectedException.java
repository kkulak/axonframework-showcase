package knbit.events.bc.eventproposal.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;

/**
 * Created by novy on 06.05.15.
 */
public class ProposalAlreadyRejectedException extends DomainException {

    private static final String MESSAGE_PATTERN = "Event proposal with id %s already accepted!";

    public ProposalAlreadyRejectedException(String eventProposalId) {
        super(String.format(MESSAGE_PATTERN, eventProposalId));
    }
}

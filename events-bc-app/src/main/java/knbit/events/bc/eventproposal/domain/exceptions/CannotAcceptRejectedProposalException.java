package knbit.events.bc.eventproposal.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;

/**
 * Created by novy on 06.05.15.
 */
public class CannotAcceptRejectedProposalException extends DomainException {

    private static final String MESSAGE_PATTERN = "Cannot accept already rejected proposal (id = %s)";

    public CannotAcceptRejectedProposalException(String eventProposalId) {
        super(String.format(MESSAGE_PATTERN, eventProposalId));
    }
}

package knbit.events.bc.eventproposal.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by novy on 22.08.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EventProposalExceptions {

    public static class ProposalAlreadyAccepted extends DomainException {

        private static final String MESSAGE_PATTERN = "Event proposal with id %s already accepted!";

        public ProposalAlreadyAccepted(String eventProposalId) {
            super(String.format(MESSAGE_PATTERN, eventProposalId));
        }
    }

    public static class ProposalAlreadyRejected extends DomainException {

        private static final String MESSAGE_PATTERN = "Event proposal with id %s already accepted!";

        public ProposalAlreadyRejected(String eventProposalId) {
            super(String.format(MESSAGE_PATTERN, eventProposalId));
        }
    }

    public static class CannotAcceptRejectedProposal extends DomainException {

        private static final String MESSAGE_PATTERN = "Cannot accept already rejected proposal (id = %s)";

        public CannotAcceptRejectedProposal(String eventProposalId) {
            super(String.format(MESSAGE_PATTERN, eventProposalId));
        }
    }

    public static class CannotRejectAcceptedProposal extends DomainException {

        private static final String MESSAGE_PATTERN = "Cannot reject already accepted proposal (id = %s)";

        public CannotRejectAcceptedProposal(String eventProposalId) {
            super(String.format(MESSAGE_PATTERN, eventProposalId));
        }
    }
}

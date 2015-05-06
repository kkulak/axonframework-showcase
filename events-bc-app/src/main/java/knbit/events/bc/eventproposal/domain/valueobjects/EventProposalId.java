package knbit.events.bc.eventproposal.domain.valueobjects;

import knbit.events.bc.common.domain.UUIDBasedIdentifier;

/**
 * Created by novy on 05.05.15.
 */

public class EventProposalId extends UUIDBasedIdentifier {

    public EventProposalId() {
    }

    protected EventProposalId(String id) {
        super(id);
    }

    public static EventProposalId of(String id) {
        return new EventProposalId(id);
    }
}

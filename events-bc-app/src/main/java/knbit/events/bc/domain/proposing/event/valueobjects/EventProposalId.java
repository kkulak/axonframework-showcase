package knbit.events.bc.domain.proposing.event.valueobjects;

import knbit.events.bc.domain.common.UUIDBasedIdentifier;

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

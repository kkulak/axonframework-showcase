package knbit.events.bc.eventproposal.domain.valueobjects.events;

import knbit.events.bc.eventproposal.domain.enums.EventType;
import knbit.events.bc.eventproposal.domain.enums.ProposalState;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 06.05.15.
 */

@Value
@Accessors(fluent = true)
public class ProposalAcceptedEvent implements ProposalStateChanged {

    private final EventProposalId eventProposalId;
    private final EventType eventType;
    private final ProposalState state;
}

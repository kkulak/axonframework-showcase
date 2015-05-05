package knbit.events.bc.domain.proposing.event.valueobjects.events;

import knbit.events.bc.domain.proposing.event.EventType;
import knbit.events.bc.domain.proposing.event.valueobjects.Description;
import knbit.events.bc.domain.proposing.event.valueobjects.EventProposalId;
import knbit.events.bc.domain.proposing.event.valueobjects.Name;
import knbit.events.bc.domain.proposing.event.valueobjects.ProposalState;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 05.05.15.
 */

@Value
@Accessors
public class EventProposed {

    private final EventProposalId id;
    private final Name name;
    private final Description description;
    private final EventType eventType;
    private final ProposalState proposalState;
}

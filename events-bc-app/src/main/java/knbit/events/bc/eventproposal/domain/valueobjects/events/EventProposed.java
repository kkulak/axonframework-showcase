package knbit.events.bc.eventproposal.domain.valueobjects.events;

import knbit.events.bc.eventproposal.domain.enums.EventType;
import knbit.events.bc.eventproposal.domain.enums.ProposalState;
import knbit.events.bc.eventproposal.domain.valueobjects.Description;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import knbit.events.bc.eventproposal.domain.valueobjects.Name;
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

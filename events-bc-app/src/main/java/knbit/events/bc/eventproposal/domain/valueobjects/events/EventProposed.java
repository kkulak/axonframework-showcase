package knbit.events.bc.eventproposal.domain.valueobjects.events;

import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.Name;
import knbit.events.bc.eventproposal.domain.enums.ProposalState;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 05.05.15.
 */

@Value
@Accessors(fluent = true)
public class EventProposed {

    private final EventProposalId eventProposalId;
    private final Name name;
    private final Description description;
    private final EventType eventType;
    private final EventFrequency eventFrequency;
    private final ProposalState proposalState;
}

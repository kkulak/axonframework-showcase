package knbit.events.bc.eventproposal.domain.valueobjects.events;

import knbit.events.bc.eventproposal.domain.enums.ProposalState;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 06.05.15.
 */
@Value
@Accessors(fluent = true)
public class ProposalRejectedEvent implements ProposalStateChanged {

    private final EventProposalId eventProposalId;
    private final ProposalState state;
}


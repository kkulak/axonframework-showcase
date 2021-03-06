package knbit.events.bc.eventproposal.domain.builders;

import knbit.events.bc.eventproposal.domain.enums.ProposalState;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import knbit.events.bc.eventproposal.domain.valueobjects.events.EventProposalEvents;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 06.05.15.
 */

@Setter
@Accessors(fluent = true)
@NoArgsConstructor(staticName = "newProposalRejectedEvent")
public class ProposalRejectedEventBuilder {

    private EventProposalId eventProposalId = EventProposalId.of("id");
    private ProposalState state = ProposalState.REJECTED;

    public EventProposalEvents.ProposalRejected build() {
        return new EventProposalEvents.ProposalRejected(eventProposalId, state);
    }
}

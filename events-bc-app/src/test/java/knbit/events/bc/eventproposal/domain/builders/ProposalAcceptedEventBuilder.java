package knbit.events.bc.eventproposal.domain.builders;

import knbit.events.bc.eventproposal.domain.enums.EventType;
import knbit.events.bc.eventproposal.domain.enums.ProposalState;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import knbit.events.bc.eventproposal.domain.valueobjects.events.ProposalAcceptedEvent;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 06.05.15.
 */

@Setter
@Accessors(fluent = true)
@NoArgsConstructor(staticName = "newProposalAcceptedEvent")
public class ProposalAcceptedEventBuilder {

    private EventProposalId eventProposalId = EventProposalId.of("id");
    private ProposalState state = ProposalState.ACCEPTED;
    private EventType eventType = EventType.LECTURE;

    public ProposalAcceptedEvent build() {
        return new ProposalAcceptedEvent(eventProposalId, eventType, state);
    }
}

package knbit.events.bc.eventproposal.domain.builders;

import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.Name;
import knbit.events.bc.eventproposal.domain.enums.ProposalState;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import knbit.events.bc.eventproposal.domain.valueobjects.events.EventProposalEvents;
import lombok.NoArgsConstructor;

/**
 * Created by novy on 05.05.15.
 */

@NoArgsConstructor(staticName = "newEventProposed")
public class EventProposedBuilder {

    private EventProposalId eventProposalId = EventProposalId.of("id");
    private Name name = Name.of("name");
    private Description description = Description.of("description");
    private EventType eventType = EventType.LECTURE;
    private ProposalState proposalState = ProposalState.PENDING;

    public EventProposedBuilder eventProposalId(EventProposalId eventProposalId) {
        this.eventProposalId = eventProposalId;
        return this;
    }

    public EventProposedBuilder name(String name) {
        this.name = Name.of(name);
        return this;
    }

    public EventProposedBuilder description(String description) {
        this.description = Description.of(description);
        return this;
    }

    public EventProposedBuilder proposalState(ProposalState proposalState) {
        this.proposalState = proposalState;
        return this;
    }


    public EventProposedBuilder eventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public EventProposalEvents.EventProposed build() {
        return new EventProposalEvents.EventProposed(eventProposalId, name, description, eventType, proposalState);
    }
}

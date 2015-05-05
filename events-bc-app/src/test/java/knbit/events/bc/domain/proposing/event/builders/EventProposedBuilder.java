package knbit.events.bc.domain.proposing.event.builders;

import knbit.events.bc.domain.proposing.event.EventType;
import knbit.events.bc.domain.proposing.event.valueobjects.Description;
import knbit.events.bc.domain.proposing.event.valueobjects.EventProposalId;
import knbit.events.bc.domain.proposing.event.valueobjects.Name;
import knbit.events.bc.domain.proposing.event.valueobjects.ProposalState;
import knbit.events.bc.domain.proposing.event.valueobjects.commands.ProposeEventCommand;
import knbit.events.bc.domain.proposing.event.valueobjects.events.EventProposed;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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

    public EventProposed build() {
        return new EventProposed(eventProposalId, name, description, eventType, proposalState);
    }
}

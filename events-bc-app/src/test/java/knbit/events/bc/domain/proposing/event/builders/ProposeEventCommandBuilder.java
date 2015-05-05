package knbit.events.bc.domain.proposing.event.builders;

import knbit.events.bc.domain.proposing.event.EventType;
import knbit.events.bc.domain.proposing.event.valueobjects.Description;
import knbit.events.bc.domain.proposing.event.valueobjects.EventProposalId;
import knbit.events.bc.domain.proposing.event.valueobjects.Name;
import knbit.events.bc.domain.proposing.event.valueobjects.commands.ProposeEventCommand;
import lombok.NoArgsConstructor;

/**
 * Created by novy on 05.05.15.
 */

@NoArgsConstructor(staticName = "newProposeEventCommand")
public class ProposeEventCommandBuilder {

    private EventProposalId eventProposalId = EventProposalId.of("id");
    private String name = "name";
    private String description = "description";
    private EventType eventType = EventType.LECTURE;

    public ProposeEventCommandBuilder eventProposalId(EventProposalId eventProposalId) {
        this.eventProposalId = eventProposalId;
        return this;
    }

    public ProposeEventCommandBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProposeEventCommandBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ProposeEventCommandBuilder eventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public ProposeEventCommand build() {
        return new ProposeEventCommand(eventProposalId, name, description, eventType);
    }
}

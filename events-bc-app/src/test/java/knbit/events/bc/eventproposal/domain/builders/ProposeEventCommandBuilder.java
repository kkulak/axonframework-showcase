package knbit.events.bc.eventproposal.domain.builders;

import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import knbit.events.bc.eventproposal.domain.valueobjects.commands.ProposeEventCommand;
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
    private EventFrequency eventFrequency = EventFrequency.ONE_OFF;

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

    public ProposeEventCommandBuilder eventFrequency(EventFrequency frequency) {
        this.eventFrequency = frequency;
        return this;
    }

    public ProposeEventCommand build() {
        return new ProposeEventCommand(eventProposalId, name, description, eventType, eventFrequency);
    }
}

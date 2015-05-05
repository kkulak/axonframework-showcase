package knbit.events.bc.domain.proposing.event.builders;

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
    private Name name = Name.of("name");
    private Description description = Description.of("description");

    public ProposeEventCommandBuilder eventProposalId(EventProposalId eventProposalId) {
        this.eventProposalId = eventProposalId;
        return this;
    }

    public ProposeEventCommandBuilder name(String name) {
        this.name = Name.of(name);
        return this;
    }

    public ProposeEventCommandBuilder description(String description) {
        this.description = Description.of(description);
        return this;
    }

    public ProposeEventCommand build() {
        return new ProposeEventCommand(eventProposalId, name, description);
    }
}

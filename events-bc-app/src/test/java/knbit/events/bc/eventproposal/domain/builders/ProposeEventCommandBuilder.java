package knbit.events.bc.eventproposal.domain.builders;

import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import knbit.events.bc.eventproposal.domain.valueobjects.commands.EventProposalCommands;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Optional;

/**
 * Created by novy on 05.05.15.
 */

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "newProposeEventCommand")
public class ProposeEventCommandBuilder {

    private EventProposalId eventProposalId = EventProposalId.of("id");
    private String name = "name";
    private String description = "description";
    private EventType eventType = EventType.LECTURE;
    private Optional<String> imageUrl = Optional.of("https://www.google.pl/");

    public EventProposalCommands.ProposeEvent build() {
        return new EventProposalCommands.ProposeEvent(eventProposalId, name, description, eventType, imageUrl);
    }
}

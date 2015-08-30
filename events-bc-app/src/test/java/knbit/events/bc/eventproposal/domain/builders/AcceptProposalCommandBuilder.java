package knbit.events.bc.eventproposal.domain.builders;

import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import knbit.events.bc.eventproposal.domain.valueobjects.commands.EventProposalCommands;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 06.05.15.
 */

@Setter
@Accessors(fluent = true)
@NoArgsConstructor(staticName = "newAcceptProposalCommand")
public class AcceptProposalCommandBuilder {

    private EventProposalId eventProposalId = EventProposalId.of("id");

    public EventProposalCommands.AcceptProposal build() {
        return new EventProposalCommands.AcceptProposal(eventProposalId);
    }

}

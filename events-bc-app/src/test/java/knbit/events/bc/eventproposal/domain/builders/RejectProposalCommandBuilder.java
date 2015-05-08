package knbit.events.bc.eventproposal.domain.builders;

import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import knbit.events.bc.eventproposal.domain.valueobjects.commands.RejectProposalCommand;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 06.05.15.
 */

@Setter
@Accessors(fluent = true)
@NoArgsConstructor(staticName = "newRejectProposalCommand")
public class RejectProposalCommandBuilder {

    private EventProposalId eventProposalId = EventProposalId.of("id");

    public RejectProposalCommand build() {
        return new RejectProposalCommand(eventProposalId);
    }

}

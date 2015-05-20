package knbit.events.bc.eventproposal.domain.valueobjects.commands;

import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 06.05.15.
 */

@Value
@Accessors(fluent = true)
public class AcceptProposalCommand {

    private final EventProposalId eventProposalId;
}

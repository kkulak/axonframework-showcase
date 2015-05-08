package knbit.events.bc.eventproposal.domain.valueobjects.commands;

import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 05.05.15.
 */

@Value
@Accessors(fluent = true)
public class ProposeEventCommand {

    private final EventProposalId eventProposalId;
    private final String name;
    private final String description;
    private final EventType eventType;
    private final EventFrequency eventFrequency;
}

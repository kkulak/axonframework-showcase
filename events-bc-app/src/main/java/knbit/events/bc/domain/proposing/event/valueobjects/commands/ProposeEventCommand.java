package knbit.events.bc.domain.proposing.event.valueobjects.commands;

import knbit.events.bc.domain.proposing.event.EventType;
import knbit.events.bc.domain.proposing.event.valueobjects.EventProposalId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 05.05.15.
 */

@Value
@Accessors
public class ProposeEventCommand {

    private final EventProposalId id;
    private final String name;
    private final String description;
    private final EventType eventType;
}

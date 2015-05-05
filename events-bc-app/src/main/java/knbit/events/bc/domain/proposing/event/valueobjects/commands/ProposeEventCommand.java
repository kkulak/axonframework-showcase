package knbit.events.bc.domain.proposing.event.valueobjects.commands;

import knbit.events.bc.domain.proposing.event.valueobjects.Description;
import knbit.events.bc.domain.proposing.event.valueobjects.EventProposalId;
import knbit.events.bc.domain.proposing.event.valueobjects.Name;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 05.05.15.
 */

@Value
@Accessors
public class ProposeEventCommand {

    private final EventProposalId id;
    private final Name name;
    private final Description description;
}

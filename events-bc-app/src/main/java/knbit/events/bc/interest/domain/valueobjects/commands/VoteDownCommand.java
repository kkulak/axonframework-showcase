package knbit.events.bc.interest.domain.valueobjects.commands;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Value
public class VoteDownCommand {

    private final EventId eventId;
    private final Attendee attendee;
}

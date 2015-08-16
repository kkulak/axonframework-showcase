package knbit.events.bc.interest.domain.valueobjects.commands;

import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 16.08.15.
 */

@Value(staticConstructor = "of")
@Accessors(fluent = true)
public class TransitInterestAwareEventToUnderTermChoosingEventCommand {

    private final EventId eventId;
}

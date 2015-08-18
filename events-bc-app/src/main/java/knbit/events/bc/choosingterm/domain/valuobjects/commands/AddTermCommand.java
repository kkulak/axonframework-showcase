package knbit.events.bc.choosingterm.domain.valuobjects.commands;

import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.Value;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Created by novy on 19.08.15.
 */

@Value(staticConstructor = "of")
@Accessors(fluent = true)
public class AddTermCommand {

    EventId eventId;
    LocalDateTime startDate;
    Duration duration;
    int capacity;
    String location;
}

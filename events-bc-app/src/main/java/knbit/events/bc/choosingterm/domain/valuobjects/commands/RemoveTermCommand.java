package knbit.events.bc.choosingterm.domain.valuobjects.commands;

import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.Value;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;
import org.joda.time.Duration;


/**
 * Created by novy on 19.08.15.
 */

@Value(staticConstructor = "of")
@Accessors(fluent = true)
public class RemoveTermCommand {

    EventId eventId;
    DateTime startDate;
    Duration duration;
    int capacity;
    String location;
}

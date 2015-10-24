package knbit.events.bc.eventready.domain.valueobjects;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTermWithAttendees;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * Created by novy on 24.10.15.
 */
public interface ReadyCommands {

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class Create {

        EventId eventId;
        EventDetails eventDetails;
        Collection<IdentifiedTermWithAttendees> terms;
    }
}

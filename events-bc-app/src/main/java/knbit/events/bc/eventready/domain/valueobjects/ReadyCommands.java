package knbit.events.bc.eventready.domain.valueobjects;

import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventId;
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

        ReadyEventId readyEventId;
        EventId correlationId;
        EventReadyDetails eventDetails;
        Collection<Attendee> attendees;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class Cancel {

        ReadyEventId readyEventId;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class MarkTookPlace {

        ReadyEventId eventId;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class ChangeDetails {

        ReadyEventId eventId;
        EventReadyDetails newDetails;
    }
}

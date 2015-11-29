package knbit.events.bc.eventready.domain.valueobjects;

import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventCancelled;
import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * Created by novy on 20.10.15.
 */
public interface ReadyEvents {

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class Created {

        ReadyEventId readyEventId;
        EventId correlationId;
        EventReadyDetails eventDetails;
        Collection<Attendee> attendees;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class DetailsChanged {

        ReadyEventId readyEventId;
        EventReadyDetails oldDetails;
        EventReadyDetails newDetails;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class TookPlace {

        ReadyEventId readyEventId;
        EventReadyDetails eventDetails;
        Collection<Attendee> attendees;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class Cancelled implements EventCancelled {

        ReadyEventId eventId;
        Collection<Attendee> attendees;
    }
}

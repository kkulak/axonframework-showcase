package knbit.events.bc.eventready.domain.valueobjects;

import knbit.events.bc.common.domain.valueobjects.Attendee;
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
}

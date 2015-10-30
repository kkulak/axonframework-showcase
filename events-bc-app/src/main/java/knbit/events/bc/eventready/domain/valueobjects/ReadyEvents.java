package knbit.events.bc.eventready.domain.valueobjects;

import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.readmodel.EventStatus;
import knbit.events.bc.common.readmodel.EventStatusAware;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * Created by novy on 20.10.15.
 */
public interface ReadyEvents {

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class Created implements EventStatusAware {

        EventId eventId;
        EventId correlationId;
        EventReadyDetails eventDetails;
        Collection<Attendee> attendees;

        @Override
        public EventStatus status() {
            return EventStatus.READY;
        }
    }
}

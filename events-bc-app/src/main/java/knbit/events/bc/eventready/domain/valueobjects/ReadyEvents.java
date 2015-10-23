package knbit.events.bc.eventready.domain.valueobjects;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.readmodel.EventStatus;
import knbit.events.bc.common.readmodel.EventStatusAware;
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTermWithAttendees;
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
        EventDetails eventDetails;
        Collection<IdentifiedTermWithAttendees> terms;

        @Override
        public EventStatus status() {
            return EventStatus.READY;
        }
    }
}

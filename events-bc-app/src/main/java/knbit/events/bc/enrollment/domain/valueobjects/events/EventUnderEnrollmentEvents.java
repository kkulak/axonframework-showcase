package knbit.events.bc.enrollment.domain.valueobjects.events;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.readmodel.EventStatus;
import knbit.events.bc.common.readmodel.EventStatusAware;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * Created by novy on 02.10.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EventUnderEnrollmentEvents {


    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class Created implements EventStatusAware {

        EventId eventId;
        EventDetails eventDetails;
        Collection<IdentifiedTerm> terms;

        @Override
        public EventStatus status() {
            return EventStatus.ENROLLMENT;
        }
    }
}

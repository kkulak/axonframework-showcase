package knbit.events.bc.eventready.domain.aggregates;

import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.EndSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by novy on 31.10.15.
 */
public class NotifyEventTookPlaceSaga extends AbstractAnnotatedSaga {
    private static final String READY_EVENT_ID_PROPERTY = "readyEventId";

    private EventScheduler eventScheduler;

    @Autowired
    public void setEventScheduler(EventScheduler eventScheduler) {
        this.eventScheduler = eventScheduler;
    }

    @StartSaga
    @EndSaga
    @SagaEventHandler(associationProperty = READY_EVENT_ID_PROPERTY)
    public void on(ReadyEvents.Created event) {
        // todo: add cancellation logic later
        final DateTime scheduleAt = event.eventDetails().duration().end();
        final ReadyEvents.TookPlace eventTookPlace =
                ReadyEvents.TookPlace.of(event.readyEventId(), event.eventDetails(), event.attendees());

        eventScheduler.schedule(scheduleAt, eventTookPlace);
    }
}

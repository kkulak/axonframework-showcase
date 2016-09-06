package knbit.events.bc.eventready.domain.aggregates;

import knbit.events.bc.eventready.domain.valueobjects.ReadyCommands;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEventId;
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents;
import lombok.Value;
import lombok.experimental.Accessors;
import org.axonframework.commandhandling.gateway.CommandGateway;
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
    private CommandGateway commandGateway;

    @Autowired
    public void setEventScheduler(EventScheduler eventScheduler) {
        this.eventScheduler = eventScheduler;
    }

    @Autowired
    public void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = READY_EVENT_ID_PROPERTY)
    public void on(ReadyEvents.Created event) {
        final DateTime scheduleAt = event.eventDetails().duration().end();
        final ReadyEventEnded eventEnded = ReadyEventEnded.of(event.readyEventId());

        eventScheduler.schedule(scheduleAt, eventEnded);
    }

    @SagaEventHandler(associationProperty = READY_EVENT_ID_PROPERTY)
    @EndSaga
    public void on(ReadyEventEnded event) {
        commandGateway.send(ReadyCommands.MarkTookPlace.of(event.readyEventId()));
    }

    @SagaEventHandler(associationProperty = "eventId", keyName = READY_EVENT_ID_PROPERTY)
    @EndSaga
    public void on(ReadyEvents.Cancelled event) {
    }
}

@Accessors(fluent = true)
@Value(staticConstructor = "of")
class ReadyEventEnded {

    ReadyEventId readyEventId;
}

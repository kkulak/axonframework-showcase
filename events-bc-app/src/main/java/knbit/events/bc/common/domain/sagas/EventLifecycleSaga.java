package knbit.events.bc.common.domain.sagas;

import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventCreated;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventTransitedToInterestAwareEvent;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventTransitedToUnderChoosingTermEvent;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.CreateUnderChoosingTermEventCommand;
import knbit.events.bc.interest.domain.valueobjects.commands.CreateInterestAwareEventCommand;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEventTransitedToUnderChoosingTermEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;

public class EventLifecycleSaga extends AbstractAnnotatedSaga {
    private static final String EVENT_ID_PROPERTY = "eventId";
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    private void on(BacklogEventCreated event) {
    }


    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    private void on(BacklogEventTransitedToInterestAwareEvent event) {
        commandGateway.send(
                CreateInterestAwareEventCommand.of(event.eventId(), event.eventDetails())
        );
    }

    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    private void on(BacklogEventTransitedToUnderChoosingTermEvent event) {
        commandGateway.send(
                CreateUnderChoosingTermEventCommand.of(event.eventId(), event.eventDetails())
        );
    }

    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    private void on(InterestAwareEventTransitedToUnderChoosingTermEvent event) {
        commandGateway.send(
                CreateUnderChoosingTermEventCommand.of(event.eventId(), event.eventDetails())
        );
    }

    @Autowired
    private void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

}

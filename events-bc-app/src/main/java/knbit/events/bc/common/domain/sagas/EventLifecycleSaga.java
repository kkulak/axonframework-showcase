package knbit.events.bc.common.domain.sagas;

import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventEvents;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventTransitionEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.UnderChoosingTermEventCommands;
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents;
import knbit.events.bc.common.domain.IdFactory;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTermWithAttendees;
import knbit.events.bc.enrollment.domain.valueobjects.commands.EventUnderEnrollmentCommands;
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents;
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails;
import knbit.events.bc.eventready.domain.valueobjects.ReadyCommands;
import knbit.events.bc.interest.domain.valueobjects.commands.InterestAwareEventCommands;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

public class EventLifecycleSaga extends AbstractAnnotatedSaga {
    private static final String EVENT_ID_PROPERTY = "eventId";
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    private void on(BacklogEventEvents.Created event) {
    }


    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    private void on(BacklogEventTransitionEvents.TransitedToInterestAware event) {
        commandGateway.send(
                InterestAwareEventCommands.Create.of(event.eventId(), event.eventDetails())
        );
    }

    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    private void on(BacklogEventTransitionEvents.TransitedToUnderChoosingTerm event) {
        commandGateway.send(
                UnderChoosingTermEventCommands.Create.of(event.eventId(), event.eventDetails())
        );
    }

    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    private void on(InterestAwareEvents.TransitedToUnderChoosingTerm event) {
        commandGateway.send(
                UnderChoosingTermEventCommands.Create.of(event.eventId(), event.eventDetails())
        );
    }

    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    private void on(UnderChoosingTermEventEvents.TransitedToEnrollment event) {
        commandGateway.send(
                EventUnderEnrollmentCommands.Create.of(event.eventId(), event.eventDetails(), event.terms())
        );
    }

    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    private void on(EventUnderEnrollmentEvents.TransitedToReady event) {
        final EventId correlationId = event.eventId();
        final EventDetails oldEventDetails = event.eventDetails();

        final Function<IdentifiedTermWithAttendees, ReadyCommands.Create> createNewEventReadyCommand =
                term -> ReadyCommands.Create.of(
                        IdFactory.readyEventId(),
                        correlationId,
                        EventReadyDetails.of(
                                oldEventDetails, term.duration(), term.limit(), term.location(), term.lecturers()
                        ),
                        term.attendees()
                );

        event.terms()
                .stream()
                .map(createNewEventReadyCommand)
                .forEach(commandGateway::send);

        end();
    }

    @Autowired
    private void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

}

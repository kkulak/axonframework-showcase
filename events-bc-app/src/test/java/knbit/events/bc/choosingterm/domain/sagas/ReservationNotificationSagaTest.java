package knbit.events.bc.choosingterm.domain.sagas;

import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class ReservationNotificationSagaTest {
    private AnnotatedSagaTestFixture fixture;
    private EventId eventId;
    private EventDetails eventDetails;

    @Before
    public void setUp() throws Exception {
        fixture = new AnnotatedSagaTestFixture(ReservationNotificationSaga.class);
        eventId = EventId.of("eventId");
        eventDetails = EventDetailsBuilder.defaultEventDetails();
    }

    @Test
    public void shouldStartReservationNotificationSagaOnChoosingTermAggregateCreation() throws Exception {
        fixture
                .whenAggregate(eventId)
                .publishes(
                        UnderChoosingTermEventEvents.Created.of(
                                eventId, eventDetails
                        )
                )
                .expectActiveSagas(1);
    }

    @Test
    public void shouldEndSagaOnTransitionToAnotherState() throws Exception {
        fixture
                .givenAggregate(eventId)
                .published(
                        UnderChoosingTermEventEvents.Created.of(
                                eventId, eventDetails
                        )
                )
                .whenAggregate(eventId)
                .publishes(
                        UnderChoosingTermEventEvents.TransitedToEnrollment.of(
                                eventId, eventDetails, Collections.emptyList()
                        )
                )
                .expectActiveSagas(0);
    }

    @Test
    public void shouldEndSagaOnEventCancellation() throws Exception {
        fixture
                .givenAggregate(eventId)
                .published(
                        UnderChoosingTermEventEvents.Created.of(
                                eventId, eventDetails
                        )
                )
                .whenAggregate(eventId)
                .publishes(
                        UnderChoosingTermEventEvents.Cancelled.of(eventId)
                )
                .expectActiveSagas(0);
    }
}

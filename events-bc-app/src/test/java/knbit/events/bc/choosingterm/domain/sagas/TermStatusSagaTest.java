package knbit.events.bc.choosingterm.domain.sagas;

import knbit.events.bc.choosingterm.domain.builders.TermBuilder;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.choosingterm.domain.valuobjects.events.ReservationEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermStatusEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents;
import knbit.events.bc.common.domain.valueobjects.EventId;
import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class TermStatusSagaTest {
    private AnnotatedSagaTestFixture fixture;
    private EventId eventId;

    @Before
    public void setUp() throws Exception {
        fixture = new AnnotatedSagaTestFixture(TermStatusSaga.class);
        eventId = EventId.of("event-id");
    }

    @Test
    public void shouldStartTermStatusSagaOnUnderChoosingTermCreated() throws Exception {
        fixture
                .whenAggregate(eventId)
                .publishes(UnderChoosingTermEventEvents.Created.of(eventId, null))
                .expectActiveSagas(1);
    }

    @Test
    public void shouldPublishTermsReadyEventOnTotalTermsAmountOne() throws Exception {
        fixture
                .givenAggregate(eventId)
                .published(UnderChoosingTermEventEvents.Created.of(eventId, null))
                .whenPublishingA(TermEvents.TermAdded.of(eventId, TermId.of("id"), TermBuilder.defaultTerm()))
                .expectPublishedEvents(TermStatusEvents.Ready.of(eventId));
    }

    @Test
    public void shouldPublishTermsPendingEventOnTotalTermsAmountZero() throws Exception {
        fixture
                .givenAggregate(eventId)
                .published(
                        UnderChoosingTermEventEvents.Created.of(eventId, null),
                        TermEvents.TermAdded.of(eventId, TermId.of("id"), TermBuilder.defaultTerm())
                )
                .whenPublishingA(TermEvents.TermRemoved.of(eventId, null))
                .expectPublishedEvents(TermStatusEvents.Pending.of(eventId));
    }

    @Test
    public void shouldPublishTermsPendingEventOnTotalReservationAmountOne() throws Exception {
        fixture
                .givenAggregate(eventId)
                .published(UnderChoosingTermEventEvents.Created.of(eventId, null))
                .whenPublishingA(ReservationEvents.RoomRequested.of(eventId, null, null, null))
                .expectPublishedEvents(TermStatusEvents.Pending.of(eventId));
    }

    @Test
    public void shouldEndOnEventTransition() throws Exception {
        fixture
                .givenAggregate(eventId)
                .published(UnderChoosingTermEventEvents.Created.of(eventId, null))
                .whenAggregate(eventId)
                .publishes(
                        UnderChoosingTermEventEvents.TransitedToEnrollment.of(eventId, null, Collections.emptyList())
                )
                .expectActiveSagas(0);
    }

    @Test
    public void shouldEndOnEventCancellation() throws Exception {
        fixture
                .givenAggregate(eventId)
                .published(UnderChoosingTermEventEvents.Created.of(eventId, null))
                .whenAggregate(eventId)
                .publishes(
                        UnderChoosingTermEventEvents.Cancelled.of(eventId)
                )
                .expectActiveSagas(0);
    }
}

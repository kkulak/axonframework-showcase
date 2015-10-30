package knbit.events.bc.common.domain.sagas;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventEvents;
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventTransitionEvents;
import knbit.events.bc.choosingterm.domain.builders.TermBuilder;
import knbit.events.bc.choosingterm.domain.valuobjects.IdentifiedTerm;
import knbit.events.bc.choosingterm.domain.valuobjects.Location;
import knbit.events.bc.choosingterm.domain.valuobjects.Term;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.UnderChoosingTermEventCommands;
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents;
import knbit.events.bc.common.domain.IdFactory;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTermWithAttendees;
import knbit.events.bc.enrollment.domain.valueobjects.commands.EventUnderEnrollmentCommands;
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents;
import knbit.events.bc.eventready.builders.IdentifiedTermWithAttendeeBuilder;
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails;
import knbit.events.bc.eventready.domain.valueobjects.ReadyCommands;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import knbit.events.bc.interest.domain.valueobjects.commands.InterestAwareEventCommands;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents;
import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collection;


@RunWith(PowerMockRunner.class)
@PrepareForTest(IdFactory.class)
public class EventLifecycleSagaTest {
    private AnnotatedSagaTestFixture fixture;
    private EventId eventId;
    private EventDetails eventDetails;

    @Before
    public void setUp() throws Exception {
        fixture = new AnnotatedSagaTestFixture(EventLifecycleSaga.class);
        eventId = EventId.of("id");
        eventDetails = EventDetailsBuilder
                .instance()
                .build();
    }

    @Test
    public void shouldStartSagaOnBacklogEventCreation() throws Exception {
        fixture
                .whenAggregate(eventId)
                .publishes(
                        BacklogEventEvents.Created.of(eventId, eventDetails)
                )
                .expectActiveSagas(1);
    }

    @Test
    public void shouldDispatchCreateInterestAwareEventOnBacklogEventTransitedToInterestAwareEvent() throws Exception {
        fixture
                .givenAggregate(eventId)
                .published(
                        BacklogEventEvents.Created.of(eventId, eventDetails)
                )
                .whenPublishingA(
                        BacklogEventTransitionEvents.TransitedToInterestAware.of(eventId, eventDetails)
                )
                .expectDispatchedCommandsEqualTo(
                        InterestAwareEventCommands.Create.of(eventId, eventDetails)
                );
    }

    @Test
    public void shouldDispatchCreateEventUnderChoosingTermCommandOnBacklogEventTransitedToChoosingTermEvent() throws Exception {
        fixture
                .givenAggregate(eventId)
                .published(
                        BacklogEventEvents.Created.of(eventId, eventDetails)
                )
                .whenPublishingA(
                        BacklogEventTransitionEvents.TransitedToUnderChoosingTerm.of(eventId, eventDetails)
                )
                .expectDispatchedCommandsEqualTo(
                        UnderChoosingTermEventCommands.Create.of(eventId, eventDetails)
                );
    }

    @Test
    public void shouldDispatchCreateEventUnderChoosingTermCommandOnInterestAwareEventTransitedToChoosingTermEvent() throws Exception {
        fixture
                .givenAggregate(eventId)
                .published(
                        BacklogEventEvents.Created.of(eventId, eventDetails)
                )
                .whenPublishingA(
                        InterestAwareEvents.TransitedToUnderChoosingTerm.of(eventId, eventDetails)
                )
                .expectDispatchedCommandsEqualTo(
                        UnderChoosingTermEventCommands.Create.of(eventId, eventDetails)
                );
    }

    @Test
    public void shouldDispatchCreateEventUnderEnrollmentCommandOnTransition() throws Exception {
        final Term term = TermBuilder.defaultTerm();
        final IdentifiedTerm identifiedTerm = IdentifiedTerm.of(TermId.of("id"), term);

        fixture
                .givenAggregate(eventId)
                .published(
                        BacklogEventEvents.Created.of(eventId, eventDetails)
                )
                .whenPublishingA(
                        UnderChoosingTermEventEvents.TransitedToEnrollment.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(identifiedTerm)
                        )
                )
                .expectDispatchedCommandsEqualTo(
                        EventUnderEnrollmentCommands.Create.of(eventId, eventDetails, ImmutableList.of(identifiedTerm))
                );
    }

    @Test
    public void shouldDispatchCreateReadyEventCommandForEachTermOnTransition() throws Exception {
        final IdentifiedTermWithAttendees firstTerm = IdentifiedTermWithAttendeeBuilder
                .instance()
                .termId(TermId.of("term1"))
                .location(Location.of("3.21A"))
                .build();

        final IdentifiedTermWithAttendees secondTerm = IdentifiedTermWithAttendeeBuilder
                .instance()
                .termId(TermId.of("term2"))
                .location(Location.of("3.21B"))
                .build();

        final Collection<IdentifiedTermWithAttendees> terms = ImmutableList.of(firstTerm, secondTerm);

        final EventId firstEventReadyId = EventId.of("firstEventReadyId");
        final EventId secondEventReadyId = EventId.of("secondEventReadyId");

        makeIdFactoryReturn(firstEventReadyId, secondEventReadyId);

        fixture
                .givenAggregate(eventId)
                .published(
                        BacklogEventEvents.Created.of(eventId, eventDetails)
                )
                .whenPublishingA(
                        EventUnderEnrollmentEvents.TransitedToReady.of(eventId, eventDetails, terms)
                )
                .expectDispatchedCommandsEqualTo(
                        ReadyCommands.Create.of(
                                firstEventReadyId,
                                eventId,
                                EventReadyDetails.of(
                                        eventDetails,
                                        firstTerm.duration(),
                                        firstTerm.limit(),
                                        firstTerm.location(),
                                        firstTerm.lecturer()
                                ),
                                firstTerm.attendees()
                        ),

                        ReadyCommands.Create.of(
                                secondEventReadyId,
                                eventId,
                                EventReadyDetails.of(
                                        eventDetails,
                                        secondTerm.duration(),
                                        secondTerm.limit(),
                                        secondTerm.location(),
                                        secondTerm.lecturer()
                                ),
                                firstTerm.attendees()
                        )
                );
    }

    private void makeIdFactoryReturn(EventId firstId, EventId secondId) {
        PowerMockito.mockStatic(IdFactory.class);
        Mockito.when(IdFactory.eventId())
                .thenReturn(firstId)
                .thenReturn(secondId);
    }
}

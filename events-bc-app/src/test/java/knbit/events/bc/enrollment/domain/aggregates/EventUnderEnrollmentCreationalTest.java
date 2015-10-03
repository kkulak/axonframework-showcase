package knbit.events.bc.enrollment.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.Location;
import knbit.events.bc.choosingterm.domain.valuobjects.Term;
import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.domain.valueobjects.Name;
import knbit.events.bc.enrollment.domain.valueobjects.TermId;
import knbit.events.bc.enrollment.domain.valueobjects.commands.EventUnderEnrollmentCommands;
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents;
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTerm;
import org.axonframework.test.FixtureConfiguration;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;

import static knbit.events.bc.matchers.WithoutFieldMatcher.matchExactlyIgnoring;

/**
 * Created by novy on 02.10.15.
 */
public class EventUnderEnrollmentCreationalTest {

    private FixtureConfiguration<EventUnderEnrollment> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.eventUnderEnrollmentFixtureConfiguration();
    }

    @Test
    public void givenCreateCommandItShouldProduceCorrespondingCreatedEvent() throws Exception {

        // todo builders to avoid setup
        final EventId eventId = EventId.of("eventId");
        final EventDetails eventDetails = EventDetails.of(
                Name.of("name"),
                Description.of("desc"),
                EventType.WORKSHOP,
                EventFrequency.ONE_OFF
        );
        final Term term = Term.of(
                EventDuration.of(DateTime.now(), Duration.standardHours(1)),
                Capacity.of(15),
                Location.of("3.21A")
        );
        final IdentifiedTerm identifiedTerm = IdentifiedTerm.of(
                TermId.of("dummyId"),
                term
        );

        final ImmutableList<EventUnderEnrollmentEvents.Created> expectedEvent = ImmutableList.of(
                EventUnderEnrollmentEvents.Created.of(
                        eventId,
                        eventDetails,
                        ImmutableList.of(identifiedTerm)
                )
        );


        fixture
                .givenNoPriorActivity()
                .when(
                        EventUnderEnrollmentCommands.Create.of(eventId, eventDetails, ImmutableList.of(term))
                )
                .expectEventsMatching(
                        matchExactlyIgnoring("terms", expectedEvent)
                );
    }
}
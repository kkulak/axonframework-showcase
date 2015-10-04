package knbit.events.bc.choosingterm.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.builders.TermBuilder;
import knbit.events.bc.choosingterm.domain.exceptions.CannotAddOverlappingTermException;
import knbit.events.bc.choosingterm.domain.exceptions.UnderChoosingTermEventExceptions;
import knbit.events.bc.choosingterm.domain.valuobjects.*;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.TermCommands;
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;

import static knbit.events.bc.matchers.WithoutFieldMatcher.matchExactlyIgnoring;


/**
 * Created by novy on 19.08.15.
 */
public class AddingTermTest {

    private FixtureConfiguration<UnderChoosingTermEvent> fixture;
    private EventId eventId;
    private EventDetails eventDetails;
    TermId termId;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.underChoosingTermEventFixtureConfiguration();
        eventId = EventId.of("eventId");
        eventDetails = EventDetailsBuilder.defaultEventDetails();
        termId = TermId.of("termId");
    }

    @Test
    public void tryingToAddValidTermShouldProduceTermAddedEvent() throws Exception {
        final Term newTerm = TermBuilder.defaultTerm();
        final ImmutableList<TermEvents.TermAdded> expectedTermAddedEvent =
                ImmutableList.of(TermEvents.TermAdded.of(eventId, termId, newTerm));

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails)
                )
                .when(
                        TermCommands.AddTerm.of(
                                eventId,
                                newTerm.duration().start(),
                                newTerm.duration().duration(),
                                newTerm.capacity().value(),
                                newTerm.location().value()
                        )
                )
                .expectEventsMatching(
                        matchExactlyIgnoring("termId", expectedTermAddedEvent)
                );
    }

    @Test
    public void shouldNotBeAbleToAddOverlappingTerm() throws Exception {
        final Term existingTerm = Term.of(
                EventDuration.of(
                        new DateTime(2015, 1, 1, 18, 30),
                        Duration.standardMinutes(90)
                ),
                Capacity.of(666),
                Location.of("3.21c")
        );

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),

                        TermEvents.TermAdded.of(eventId, termId, existingTerm)

                )
                .when(
                        TermCommands.AddTerm.of(
                                eventId,
                                new DateTime(2015, 1, 1, 19, 0),
                                Duration.standardMinutes(90),
                                66,
                                "3.21c"
                        )
                )
                .expectException(CannotAddOverlappingTermException.class);
    }

    @Test
    public void shouldNotBeAbleToAddTermIfEventAlreadyTransited() throws Exception {
        final Term existingTerm = TermBuilder.defaultTerm();

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),

                        TermEvents.TermAdded.of(eventId, termId, existingTerm),

                        UnderChoosingTermEventEvents.TransitedToEnrollment.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(IdentifiedTerm.of(termId, existingTerm))
                        )
                )
                .when(
                        TermCommands.AddTerm.of(
                                eventId,
                                new DateTime(2015, 1, 1, 19, 0),
                                Duration.standardMinutes(90),
                                66,
                                "4.21A"
                        )
                )
                .expectException(UnderChoosingTermEventExceptions.AlreadyTransitedToEnrollment.class);
    }
}

package knbit.events.bc.choosingterm.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.exceptions.CannotAddOverlappingTermException;
import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.Location;
import knbit.events.bc.choosingterm.domain.valuobjects.Term;
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


/**
 * Created by novy on 19.08.15.
 */
public class AddingTermTest {

    private FixtureConfiguration<UnderChoosingTermEvent> fixture;
    private EventId eventId;
    private EventDetails eventDetails;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.underChoosingTermEventFixtureConfiguration();
        eventId = EventId.of("eventId");
        eventDetails = EventDetailsBuilder
                .instance()
                .build();
    }

    @Test
    public void tryingToAddValidTermShouldProduceTermAddedEvent() throws Exception {
        final Term newTerm = Term.of(
                EventDuration.of(
                        new DateTime(2015, 1, 1, 18, 30),
                        Duration.standardMinutes(90)
                ),
                Capacity.of(666),
                Location.of("3.28c")
        );

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
                                newTerm.location().getValue()
                        )
                )
                .expectEvents(
                        TermEvents.TermAdded.of(eventId, newTerm)
                );
    }

    // todo: builders maybe
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

                        TermEvents.TermAdded.of(eventId, existingTerm)

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
}

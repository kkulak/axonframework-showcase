package knbit.events.bc.choosingterm.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.exceptions.CannotRemoveNotExistingTermException;
import knbit.events.bc.choosingterm.domain.exceptions.UnderChoosingTermEventExceptions;
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
public class RemovingTermTest {

    private FixtureConfiguration<UnderChoosingTermEvent> fixture;
    private EventId eventId;
    private EventDetails eventDetails;
    private Term termToRemove;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.underChoosingTermEventFixtureConfiguration();
        eventId = EventId.of("eventId");
        eventDetails = EventDetailsBuilder
                .instance()
                .build();
        termToRemove = Term.of(
                EventDuration.of(
                        new DateTime(2015, 1, 1, 18, 30),
                        Duration.standardMinutes(90)
                ),
                Capacity.of(666),
                Location.of("3.28c")
        );
    }

    @Test
    public void shouldNotBeAbleToRemoveNotExistingTerm() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails)
                )
                .when(
                        TermCommands.RemoveTerm.of(
                                eventId,
                                termToRemove.duration().start(),
                                termToRemove.duration().duration(),
                                termToRemove.capacity().value(),
                                termToRemove.location().value()
                        )
                )
                .expectException(CannotRemoveNotExistingTermException.class);
    }

    @Test
    public void shouldProduceTermRemovedEventOnSuccessfulRemoval() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        TermEvents.TermAdded.of(eventId, termToRemove)
                )
                .when(
                        TermCommands.RemoveTerm.of(
                                eventId,
                                termToRemove.duration().start(),
                                termToRemove.duration().duration(),
                                termToRemove.capacity().value(),
                                termToRemove.location().value()
                        )
                )
                .expectEvents(
                        TermEvents.TermRemoved.of(eventId, termToRemove)
                );
    }


    @Test
    public void shouldNotBeAbleRemoveTermIfEventTransitedToEnrollment() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),

                        TermEvents.TermAdded.of(eventId, termToRemove),

                        UnderChoosingTermEventEvents.TransitedToEnrollment.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(termToRemove)
                        )
                )
                .when(
                        TermCommands.RemoveTerm.of(
                                eventId,
                                termToRemove.duration().start(),
                                termToRemove.duration().duration(),
                                termToRemove.capacity().value(),
                                termToRemove.location().value()
                        )
                )
                .expectException(
                        UnderChoosingTermEventExceptions.AlreadyTransitedToEnrollment.class
                );
    }
}

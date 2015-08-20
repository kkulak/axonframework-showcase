package knbit.events.bc.choosingterm.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.exceptions.CannotAddOverlappingTermException;
import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.Location;
import knbit.events.bc.choosingterm.domain.valuobjects.Term;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.AddTermCommand;
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermAddedEvent;
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventCreated;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

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
                        LocalDateTime.of(2015, 1, 1, 18, 30),
                        Duration.ofMinutes(90)
                ),
                Capacity.of(666),
                Location.of("3.28c")
        );

        fixture
                .given(
                        UnderChoosingTermEventCreated.of(eventId, eventDetails)
                )
                .when(
                        AddTermCommand.of(
                                eventId,
                                newTerm.duration().start(),
                                newTerm.duration().duration(),
                                newTerm.capacity().value(),
                                newTerm.location().getValue()
                        )
                )
                .expectEvents(
                        TermAddedEvent.of(eventId, newTerm)
                );
    }

    // todo: builders maybe
    @Test
    public void shouldNotBeAbleToAddOverlappingTerm() throws Exception {
        final Term existingTerm = Term.of(
                EventDuration.of(
                        LocalDateTime.of(2015, 1, 1, 18, 30),
                        Duration.ofMinutes(90)
                ),
                Capacity.of(666),
                Location.of("3.21c")
        );

        fixture
                .given(
                        UnderChoosingTermEventCreated.of(eventId, eventDetails),

                        TermAddedEvent.of(eventId, existingTerm)

                )
                .when(
                        AddTermCommand.of(
                                eventId,
                                LocalDateTime.of(2015, 1, 1, 19, 0),
                                Duration.ofMinutes(90),
                                66,
                                "3.21c"
                        )
                )
                .expectException(CannotAddOverlappingTermException.class);
    }
}

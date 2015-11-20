package knbit.events.bc.choosingterm.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.builders.TermBuilder;
import knbit.events.bc.choosingterm.domain.exceptions.CannotRemoveNotExistingTermException;
import knbit.events.bc.choosingterm.domain.exceptions.UnderChoosingTermEventExceptions;
import knbit.events.bc.choosingterm.domain.valuobjects.EnrollmentIdentifiedTerm;
import knbit.events.bc.choosingterm.domain.valuobjects.Term;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.choosingterm.domain.valuobjects.commands.TermCommands;
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.builders.EnrollmentIdentifiedTermBuilder;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
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
    private TermId termId;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.underChoosingTermEventFixtureConfiguration();
        eventId = EventId.of("eventId");
        eventDetails = EventDetailsBuilder.defaultEventDetails();
        termToRemove = TermBuilder.defaultTerm();
        termId = TermId.of("termId");
    }

    @Test
    public void shouldNotBeAbleToRemoveNotExistingTerm() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails)
                )
                .when(
                        TermCommands.RemoveTerm.of(eventId, termId)
                )
                .expectException(CannotRemoveNotExistingTermException.class);
    }

    @Test
    public void shouldProduceTermRemovedEventOnSuccessfulRemoval() throws Exception {
        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),
                        TermEvents.TermAdded.of(eventId, termId, termToRemove)
                )
                .when(
                        TermCommands.RemoveTerm.of(eventId, termId)
                )
                .expectEvents(
                        TermEvents.TermRemoved.of(eventId, termId)
                );
    }

    @Test
    public void shouldNotBeAbleRemoveTermIfEventTransitedToEnrollment() throws Exception {
        final EnrollmentIdentifiedTerm enrollmentIdentifiedTerm = EnrollmentIdentifiedTermBuilder.instance()
                .termId(termId)
                .term(termToRemove)
                .build();

        fixture
                .given(
                        UnderChoosingTermEventEvents.Created.of(eventId, eventDetails),

                        TermEvents.TermAdded.of(eventId, termId, termToRemove),

                        UnderChoosingTermEventEvents.TransitedToEnrollment.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(enrollmentIdentifiedTerm)
                        )
                )
                .when(
                        TermCommands.RemoveTerm.of(eventId, termId)
                )
                .expectException(
                        UnderChoosingTermEventExceptions.AlreadyTransitedToEnrollment.class
                );
    }
}

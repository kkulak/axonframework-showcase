package knbit.events.bc.enrollment.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.builders.TermBuilder;
import knbit.events.bc.choosingterm.domain.valuobjects.IdentifiedTerm;
import knbit.events.bc.choosingterm.domain.valuobjects.Term;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.commands.EventUnderEnrollmentCommands;
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

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

        final EventId eventId = EventId.of("eventId");
        final EventDetails eventDetails = EventDetailsBuilder.defaultEventDetails();
        final Term term = TermBuilder.defaultTerm();
        final IdentifiedTerm identifiedTerm = IdentifiedTerm.of(
                TermId.of("dummyId"),
                term
        );

        fixture
                .givenNoPriorActivity()
                .when(
                        EventUnderEnrollmentCommands.Create.of(eventId, eventDetails, ImmutableList.of(identifiedTerm))
                )
                .expectEvents(
                        EventUnderEnrollmentEvents.Created.of(
                                eventId,
                                eventDetails,
                                ImmutableList.of(identifiedTerm)
                        )
                );
    }
}
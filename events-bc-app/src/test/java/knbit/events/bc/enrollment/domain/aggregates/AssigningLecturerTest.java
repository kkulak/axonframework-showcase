package knbit.events.bc.enrollment.domain.aggregates;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.FixtureFactory;
import knbit.events.bc.choosingterm.domain.builders.TermBuilder;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.exceptions.EventUnderEnrollmentExceptions;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.enrollment.domain.valueobjects.commands.TermModifyingCommands;
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents;
import knbit.events.bc.choosingterm.domain.valuobjects.IdentifiedTerm;
import knbit.events.bc.enrollment.domain.valueobjects.events.TermModifyingEvents;
import knbit.events.bc.interest.builders.EventDetailsBuilder;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 03.10.15.
 */
public class AssigningLecturerTest {

    private FixtureConfiguration<EventUnderEnrollment> fixture;
    private EventId eventId;
    private EventDetails eventDetails;
    private IdentifiedTerm identifiedTerm;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.eventUnderEnrollmentFixtureConfiguration();
        eventId = EventId.of("id");
        eventDetails = EventDetailsBuilder.defaultEventDetails();
        identifiedTerm = IdentifiedTerm.of(TermId.of("id"), TermBuilder.defaultTerm());
    }

    @Test
    public void shouldNotBeAbleToAssignLecturerOnNotExistingTerm() throws Exception {

        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(eventId, eventDetails, ImmutableList.of(identifiedTerm))
                )
                .when(
                        TermModifyingCommands.AssignLecturer.of(eventId, TermId.of("fakeId"), "firstName", "lastName")
                )
                .expectException(
                        EventUnderEnrollmentExceptions.NoSuchTermException.class
                );
    }

    @Test
    public void otherwiseItShouldProduceProperEvent() throws Exception {

        final Lecturer lecturer = Lecturer.of("firstName", "lastName");

        fixture
                .given(
                        EventUnderEnrollmentEvents.Created.of(eventId, eventDetails, ImmutableList.of(identifiedTerm))
                )
                .when(
                        TermModifyingCommands.AssignLecturer.of(
                                eventId,
                                identifiedTerm.termId(),
                                lecturer.firstName(),
                                lecturer.lastName()
                        )
                )
                .expectEvents(
                        TermModifyingEvents.LecturerAssigned.of(eventId, identifiedTerm.termId(), lecturer)
                );
    }
}

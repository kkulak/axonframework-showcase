package knbit.events.bc;

import knbit.events.bc.backlogevent.domain.BacklogEventCommandHandler;
import knbit.events.bc.backlogevent.domain.aggregates.BacklogEvent;
import knbit.events.bc.choosingterm.domain.UnderChoosingTermEventCommandHandler;
import knbit.events.bc.choosingterm.domain.aggregates.UnderChoosingTermEvent;
import knbit.events.bc.enrollment.domain.EventUnderEnrollmentCommandHandler;
import knbit.events.bc.enrollment.domain.aggregates.EventUnderEnrollment;
import knbit.events.bc.eventproposal.domain.EventProposalCommandHandler;
import knbit.events.bc.eventproposal.domain.aggregates.EventProposal;
import knbit.events.bc.interest.domain.InterestAwareEventCommandHandler;
import knbit.events.bc.interest.domain.aggregates.InterestAwareEvent;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;

/**
 * Created by novy on 05.05.15.
 */
public class FixtureFactory {

    public static FixtureConfiguration<EventProposal> eventProposalFixtureConfiguration() {
        FixtureConfiguration<EventProposal> fixture = Fixtures.newGivenWhenThenFixture(EventProposal.class);

        EventProposalCommandHandler commandHandler = new EventProposalCommandHandler(
                fixture.getRepository()
        );

        fixture.registerAnnotatedCommandHandler(commandHandler);
        return fixture;
    }

    public static FixtureConfiguration<BacklogEvent> backlogEventFixtureConfiguration() {
        FixtureConfiguration<BacklogEvent> fixture = Fixtures.newGivenWhenThenFixture(BacklogEvent.class);
        final BacklogEventCommandHandler handler = new BacklogEventCommandHandler(fixture.getRepository());
        fixture.registerAnnotatedCommandHandler(handler);
        return fixture;
    }

    public static FixtureConfiguration<InterestAwareEvent> interestAwareEventFixtureConfiguration() {
        FixtureConfiguration<InterestAwareEvent> fixture = Fixtures.newGivenWhenThenFixture(InterestAwareEvent.class);

        final InterestAwareEventCommandHandler handler = new InterestAwareEventCommandHandler(
                fixture.getRepository()
        );

        fixture.registerAnnotatedCommandHandler(handler);
        return fixture;
    }

    public static FixtureConfiguration<UnderChoosingTermEvent> underChoosingTermEventFixtureConfiguration() {
        FixtureConfiguration<UnderChoosingTermEvent> fixture = Fixtures.newGivenWhenThenFixture(UnderChoosingTermEvent.class);

        final UnderChoosingTermEventCommandHandler handler = new UnderChoosingTermEventCommandHandler(
                fixture.getRepository()
        );

        fixture.registerAnnotatedCommandHandler(handler);
        return fixture;
    }

    public static FixtureConfiguration<EventUnderEnrollment> eventUnderEnrollmentFixtureConfiguration() {
        FixtureConfiguration<EventUnderEnrollment> fixture = Fixtures.newGivenWhenThenFixture(EventUnderEnrollment.class);

        final EventUnderEnrollmentCommandHandler handler = new EventUnderEnrollmentCommandHandler(
                fixture.getRepository()
        );

        fixture.registerAnnotatedCommandHandler(handler);
        return fixture;
    }
}

package knbit.events.bc;

import knbit.events.bc.backlogevent.domain.EventCommandHandler;
import knbit.events.bc.backlogevent.domain.aggregates.BacklogEvent;
import knbit.events.bc.eventproposal.domain.EventProposalCommandHandler;
import knbit.events.bc.eventproposal.domain.aggregates.EventProposal;
import knbit.events.bc.interest.domain.InterestAwareEventCommandHandler;
import knbit.events.bc.interest.domain.aggregates.InterestAwareEvent;
import knbit.events.bc.interest.questionnaire.domain.QuestionnaireCommandHandler;
import knbit.events.bc.interest.questionnaire.domain.aggregates.Questionnaire;
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
        final EventCommandHandler handler = new EventCommandHandler(fixture.getRepository());
        fixture.registerAnnotatedCommandHandler(handler);
        return fixture;
    }

    public static FixtureConfiguration<Questionnaire> questionnaireFixtureConfiguration() {
        FixtureConfiguration<Questionnaire> fixture = Fixtures.newGivenWhenThenFixture(Questionnaire.class);

        final QuestionnaireCommandHandler handler = new QuestionnaireCommandHandler(
                fixture.getRepository()
        );

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

}

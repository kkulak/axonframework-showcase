package knbit.events.bc;

import knbit.events.bc.event.domain.EventCommandHandler;
import knbit.events.bc.event.domain.aggregates.AbstractEvent;
import knbit.events.bc.event.infrastructure.config.AxonEventFactory;
import knbit.events.bc.eventproposal.domain.EventProposalCommandHandler;
import knbit.events.bc.eventproposal.domain.aggregates.EventProposal;
import knbit.events.bc.interestsurvey.domain.SurveyCommandHandler;
import knbit.events.bc.interestsurvey.domain.aggreagates.Survey;
import knbit.events.bc.questionnaire.domain.QuestionnaireCommandHandler;
import knbit.events.bc.questionnaire.domain.aggregates.Questionnaire;
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

    public static FixtureConfiguration<AbstractEvent> eventFixtureConfiguration() {
        FixtureConfiguration<AbstractEvent> fixture = Fixtures.newGivenWhenThenFixture(AbstractEvent.class);
        fixture.registerAggregateFactory(new AxonEventFactory());
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

    public static FixtureConfiguration<Survey> surveyFixtureConfiguration() {
        FixtureConfiguration<Survey> fixture = Fixtures.newGivenWhenThenFixture(Survey.class);

        final SurveyCommandHandler handler = new SurveyCommandHandler(
                fixture.getRepository()
        );

        fixture.registerAnnotatedCommandHandler(handler);
        return fixture;
    }

}

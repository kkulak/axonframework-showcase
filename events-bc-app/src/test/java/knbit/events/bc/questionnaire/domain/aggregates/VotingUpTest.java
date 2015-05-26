package knbit.events.bc.questionnaire.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 26.05.15.
 */
public class VotingUpTest {

    private FixtureConfiguration<Questionnaire> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.questionnaireFixtureConfiguration();
    }

    @Test
    public void shouldProduceEventVotedUpWhenVoteUpCommandInvoked() throws Exception {

    }

    @Test
    public void shouldNotBeAbleToVoteDownMoreThanOnce() throws Exception {


    }
}

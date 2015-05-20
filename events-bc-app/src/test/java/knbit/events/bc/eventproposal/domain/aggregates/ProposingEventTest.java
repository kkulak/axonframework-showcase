package knbit.events.bc.eventproposal.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.eventproposal.domain.builders.EventProposedBuilder;
import knbit.events.bc.eventproposal.domain.builders.ProposeEventCommandBuilder;
import knbit.events.bc.eventproposal.domain.enums.ProposalState;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 05.05.15.
 */
public class ProposingEventTest {

    private FixtureConfiguration<EventProposal> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.eventProposalFixtureConfiguration();
    }

    @Test
    public void shouldCreateEventProposalWithPendingStateGivenProposeEventCommand() throws Exception {

        final EventProposalId anId = EventProposalId.of("id");

        fixture.given()
                .when(
                        ProposeEventCommandBuilder
                                .newProposeEventCommand()
                                .eventProposalId(anId)
                                .build()
                )
                .expectEvents(
                        EventProposedBuilder
                                .newEventProposed()
                                .eventProposalId(anId)
                                .proposalState(ProposalState.PENDING)
                                .build()
                );


    }
}
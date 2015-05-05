package knbit.events.bc.domain.proposing.event.aggregates;

import knbit.events.bc.domain.FixtureFactory;
import knbit.events.bc.domain.proposing.event.builders.EventProposedBuilder;
import knbit.events.bc.domain.proposing.event.builders.ProposeEventCommandBuilder;
import knbit.events.bc.domain.proposing.event.valueobjects.EventProposalId;
import knbit.events.bc.domain.proposing.event.valueobjects.ProposalState;
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
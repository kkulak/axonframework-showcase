package knbit.events.bc.eventproposal.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.eventproposal.domain.builders.EventProposedBuilder;
import knbit.events.bc.eventproposal.domain.builders.ProposalAcceptedEventBuilder;
import knbit.events.bc.eventproposal.domain.builders.ProposalRejectedEventBuilder;
import knbit.events.bc.eventproposal.domain.builders.RejectProposalCommandBuilder;
import knbit.events.bc.eventproposal.domain.exceptions.EventProposalExceptions;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 06.05.15.
 */
public class RejectingProposalTest {

    private FixtureConfiguration<EventProposal> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.eventProposalFixtureConfiguration();
    }

    @Test
    public void shouldNotBeAbleToRejectAcceptedProposal() throws Exception {
        final EventProposalId anId = EventProposalId.of("id");

        fixture
                .given(
                        EventProposedBuilder
                                .newEventProposed()
                                .eventProposalId(anId)
                                .build(),

                        ProposalAcceptedEventBuilder
                                .newProposalAcceptedEvent()
                                .eventProposalId(anId)
                                .build()
                )
                .when(
                        RejectProposalCommandBuilder
                                .newRejectProposalCommand()
                                .eventProposalId(anId)
                                .build()

                )
                .expectException(EventProposalExceptions.CannotRejectAcceptedProposal.class);

    }

    @Test
    public void shouldNotBeAbleToRejectAlreadyRejectedProposal() throws Exception {
        final EventProposalId anId = EventProposalId.of("id");

        fixture
                .given(
                        EventProposedBuilder
                                .newEventProposed()
                                .eventProposalId(anId)
                                .build(),

                        ProposalRejectedEventBuilder
                                .newProposalRejectedEvent()
                                .eventProposalId(anId)
                                .build()
                )
                .when(
                        RejectProposalCommandBuilder
                                .newRejectProposalCommand()
                                .eventProposalId(anId)
                                .build()

                )
                .expectException(EventProposalExceptions.ProposalAlreadyRejected.class);

    }

    @Test
    public void shouldProduceProposalRejectedEventOtherwise() throws Exception {
        final EventProposalId anId = EventProposalId.of("id");

        fixture
                .given(
                        EventProposedBuilder
                                .newEventProposed()
                                .eventProposalId(anId)
                                .build()
                )
                .when(
                        RejectProposalCommandBuilder
                                .newRejectProposalCommand()
                                .eventProposalId(anId)
                                .build()

                )
                .expectEvents(
                        ProposalRejectedEventBuilder
                                .newProposalRejectedEvent()
                                .eventProposalId(anId)
                                .build()
                );

    }
}


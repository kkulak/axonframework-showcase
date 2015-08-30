package knbit.events.bc.eventproposal.domain.aggregates;

import knbit.events.bc.FixtureFactory;
import knbit.events.bc.eventproposal.domain.builders.AcceptProposalCommandBuilder;
import knbit.events.bc.eventproposal.domain.builders.EventProposedBuilder;
import knbit.events.bc.eventproposal.domain.builders.ProposalAcceptedEventBuilder;
import knbit.events.bc.eventproposal.domain.builders.ProposalRejectedEventBuilder;
import knbit.events.bc.eventproposal.domain.exceptions.EventProposalExceptions;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by novy on 06.05.15.
 */
public class AcceptingProposalTest {

    private FixtureConfiguration<EventProposal> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = FixtureFactory.eventProposalFixtureConfiguration();
    }

    @Test
    public void shouldNotBeAbleToAcceptRejectedProposal() throws Exception {
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
                        AcceptProposalCommandBuilder
                                .newAcceptProposalCommand()
                                .eventProposalId(anId)
                                .build()

                )
                .expectException(EventProposalExceptions.CannotAcceptRejectedProposal.class);

    }

    @Test
    public void shouldNotBeAbleToAcceptAlreadyAcceptedProposal() throws Exception {
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
                        AcceptProposalCommandBuilder
                                .newAcceptProposalCommand()
                                .eventProposalId(anId)
                                .build()

                )
                .expectException(EventProposalExceptions.ProposalAlreadyAccepted.class);

    }

    @Test
    public void shouldProduceProposalAcceptedEventOtherwise() throws Exception {
        final EventProposalId anId = EventProposalId.of("id");

        fixture
                .given(
                        EventProposedBuilder
                                .newEventProposed()
                                .eventProposalId(anId)
                                .build()
                )
                .when(
                        AcceptProposalCommandBuilder
                                .newAcceptProposalCommand()
                                .eventProposalId(anId)
                                .build()

                )
                .expectEvents(
                        ProposalAcceptedEventBuilder
                                .newProposalAcceptedEvent()
                                .eventProposalId(anId)
                                .build()
                );

    }
}

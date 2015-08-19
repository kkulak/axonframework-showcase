package knbit.events.bc.eventproposal.domain.sagas;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.backlogevent.domain.builders.CreateBacklogEventCommandBuilder;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.eventproposal.domain.builders.EventProposedBuilder;
import knbit.events.bc.eventproposal.domain.builders.ProposalAcceptedEventBuilder;
import knbit.events.bc.eventproposal.domain.builders.ProposalRejectedEventBuilder;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.junit.Before;
import org.junit.Test;

import static knbit.events.bc.matchers.WithoutIdentifierMatcher.matchExactlyIgnoring;

/**
 * Created by novy on 07.05.15.
 */

public class EventCreationalSagaTest {

    private AnnotatedSagaTestFixture fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AnnotatedSagaTestFixture(EventCreationalSaga.class);
    }

    @Test
    public void shouldEndWhenCorrespondingProposalIsRejected() throws Exception {

        final EventProposalId eventProposalId = EventProposalId.of("anId");

        fixture
                .givenAggregate(eventProposalId)
                .published(
                        EventProposedBuilder
                                .newEventProposed()
                                .eventProposalId(eventProposalId)
                                .build()
                )
                .whenPublishingA(
                        ProposalRejectedEventBuilder
                                .newProposalRejectedEvent()
                                .eventProposalId(eventProposalId)
                                .build()
                )
                .expectActiveSagas(0);
    }

    @Test
    public void shouldEndWhenCorrespondingProposalIsAccepted() throws Exception {

        final EventProposalId eventProposalId = EventProposalId.of("anId");

        fixture
                .givenAggregate(eventProposalId)
                .published(
                        EventProposedBuilder
                                .newEventProposed()
                                .eventProposalId(eventProposalId)
                                .build()
                )
                .whenPublishingA(
                        ProposalAcceptedEventBuilder
                                .newProposalAcceptedEvent()
                                .eventProposalId(eventProposalId)
                                .build()
                )
                .expectActiveSagas(0);
    }

    @Test
    public void shouldProduceCreateEventCommandWhenCorrespondingProposalIsAccepted() throws Exception {

        final EventProposalId eventProposalId = EventProposalId.of("anId");
        final String proposalName = "proposalName";
        final String proposalDescription = "proposalDescription";
        final EventType proposalType = EventType.LECTURE;

        fixture
                .givenAggregate(eventProposalId)
                .published(
                        EventProposedBuilder
                                .newEventProposed()
                                .eventProposalId(eventProposalId)
                                .name(proposalName)
                                .description(proposalDescription)
                                .eventType(proposalType)
                                .build()
                )
                .whenPublishingA(
                        ProposalAcceptedEventBuilder
                                .newProposalAcceptedEvent()
                                .eventProposalId(eventProposalId)
                                .build()
                )
                .expectDispatchedCommandsMatching(
                        matchExactlyIgnoring(
                                "eventId",
                                ImmutableList.of(
                                        CreateBacklogEventCommandBuilder
                                                .newCreateBacklogEventCommand()
                                                .name(proposalName)
                                                .description(proposalDescription)
                                                .eventType(proposalType)
                                                .build()

                                )
                        )

                );
    }
}
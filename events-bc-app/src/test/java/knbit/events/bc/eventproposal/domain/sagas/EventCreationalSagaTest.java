package knbit.events.bc.eventproposal.domain.sagas;

import knbit.events.bc.backlogevent.domain.builders.CreateBacklogEventCommandBuilder;
import knbit.events.bc.common.domain.IdFactory;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.domain.valueobjects.Name;
import knbit.events.bc.common.domain.valueobjects.Section;
import knbit.events.bc.eventproposal.domain.builders.EventProposedBuilder;
import knbit.events.bc.eventproposal.domain.builders.ProposalAcceptedEventBuilder;
import knbit.events.bc.eventproposal.domain.builders.ProposalRejectedEventBuilder;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Optional;

/**
 * Created by novy on 07.05.15.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(IdFactory.class)
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
        final Name proposalName = Name.of("proposalName");
        final Description proposalDescription = Description.of("proposalDescription");
        final EventType proposalType = EventType.LECTURE;
        final EventId randomEventId = EventId.of("eventId");
        makeIdFactoryReturn(randomEventId);

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
                .expectDispatchedCommandsEqualTo(
                        CreateBacklogEventCommandBuilder
                                .newCreateBacklogEventCommand()
                                .eventId(randomEventId)
                                .name(proposalName)
                                .description(proposalDescription)
                                .eventType(proposalType)
                                .section(null)
                                .build()
                );
    }

    private void makeIdFactoryReturn(EventId eventId) {
        PowerMockito.mockStatic(IdFactory.class);
        Mockito.when(IdFactory.eventId()).thenReturn(eventId);
    }
}
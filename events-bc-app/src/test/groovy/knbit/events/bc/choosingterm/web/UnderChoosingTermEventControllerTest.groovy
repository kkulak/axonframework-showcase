package knbit.events.bc.choosingterm.web

import knbit.events.bc.backlogevent.domain.valueobjects.commands.BacklogEventCommands

import knbit.events.bc.choosingterm.domain.valuobjects.commands.AddTermCommand
import knbit.events.bc.choosingterm.domain.valuobjects.commands.BookRoomCommand
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.interest.domain.valueobjects.commands.TransitInterestAwareEventToUnderTermChoosingEventCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.joda.time.DateTime
import org.joda.time.Duration
import spock.lang.Specification

import static knbit.events.bc.choosingterm.web.TermsDTO.TermDTO
import static knbit.events.bc.choosingterm.web.TermsDTO.TermProposalDTO
import static knbit.events.bc.choosingterm.web.UnderChoosingTermEventController.TransitFrom

/**
 * Created by novy on 16.08.15.
 */
class UnderChoosingTermEventControllerTest extends Specification {

    def CommandGateway commandGatewayMock

    void setup() {
        commandGatewayMock = Mock(CommandGateway)
    }

    def "given BACKLOG as fromState query param, should dispatch command to transit from BacklogEvent"() {
        given:
        def objectUnderTest = new UnderChoosingTermEventController(commandGatewayMock)

        when:
        def emptyDto = new TermsDTO([], [])
        objectUnderTest.startChoosingTerm("eventId", TransitFrom.BACKLOG, emptyDto)

        then:
        1 * commandGatewayMock.sendAndWait(
                BacklogEventCommands.TransitToUnderChoosingTermEventCommand.of(
                        EventId.of("eventId")
                )
        )
    }

    def "given SURVEY_INTEREST as fromState query param, should dispatch command to transit from InterestAwareEvent"() {
        given:
        def objectUnderTest = new UnderChoosingTermEventController(commandGatewayMock)

        when:
        def emptyDto = new TermsDTO([], [])
        objectUnderTest.startChoosingTerm("eventId", TransitFrom.SURVEY_INTEREST, emptyDto)

        then:
        1 * commandGatewayMock.sendAndWait(
                TransitInterestAwareEventToUnderTermChoosingEventCommand.of(
                        EventId.of("eventId")
                )
        )
    }

    def "should transform all TermDTOs to corresponding AddTerm commands"() {
        given:
        def firstTermDTO = new TermDTO(DateTime.now(), 120, 60, "3.27A")
        def secondTermDTO = new TermDTO(DateTime.now(), 120, 60, "3.21A")
        def objectUnderTest = new UnderChoosingTermEventController(commandGatewayMock)

        when:
        def termsDto = new TermsDTO([firstTermDTO, secondTermDTO], [])
        objectUnderTest.startChoosingTerm("eventId", TransitFrom.SURVEY_INTEREST, termsDto)

        then:
        1 * commandGatewayMock.sendAndWait(
                AddTermCommand.of(
                        EventId.of("eventId"),
                        firstTermDTO.date,
                        Duration.standardMinutes(firstTermDTO.duration),
                        firstTermDTO.capacity,
                        firstTermDTO.location
                )
        )

        1 * commandGatewayMock.sendAndWait(
                AddTermCommand.of(
                        EventId.of("eventId"),
                        secondTermDTO.date,
                        Duration.standardMinutes(secondTermDTO.duration),
                        secondTermDTO.capacity,
                        secondTermDTO.location
                )
        )
    }


    def "should transform all TermProposalDTOs to corresponding BookRoom commands"() {
        given:
        def firstTermProposalDTO = new TermProposalDTO(DateTime.now(), 120, 60)
        def secondTermProposalDTO = new TermProposalDTO(DateTime.now(), 120, 60)
        def objectUnderTest = new UnderChoosingTermEventController(commandGatewayMock)

        when:
        def termsDto = new TermsDTO([], [firstTermProposalDTO, secondTermProposalDTO])
        objectUnderTest.startChoosingTerm("eventId", TransitFrom.SURVEY_INTEREST, termsDto)

        then:
        1 * commandGatewayMock.sendAndWait(
                BookRoomCommand.of(
                        EventId.of("eventId"),
                        firstTermProposalDTO.date,
                        Duration.standardMinutes(firstTermProposalDTO.duration),
                        firstTermProposalDTO.capacity
                )
        )

        1 * commandGatewayMock.sendAndWait(
                BookRoomCommand.of(
                        EventId.of("eventId"),
                        secondTermProposalDTO.date,
                        Duration.standardMinutes(secondTermProposalDTO.duration),
                        secondTermProposalDTO.capacity
                )
        )
    }
}

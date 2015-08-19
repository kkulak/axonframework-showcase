package knbit.events.bc.choosingterm.web

import knbit.events.bc.backlogevent.domain.valueobjects.commands.TransitBacklogEventToUnderChoosingTermEventCommand
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.interest.domain.valueobjects.commands.TransitInterestAwareEventToUnderTermChoosingEventCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import spock.lang.Specification

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
        objectUnderTest.startChoosingTerm("eventId", TransitFrom.BACKLOG)

        then:
        1 * commandGatewayMock.sendAndWait(
                TransitBacklogEventToUnderChoosingTermEventCommand.of(
                        EventId.of("eventId")
                )
        )
    }

    def "given SURVEY_INTEREST as fromState query param, should dispatch command to transit from InterestAwareEvent"() {
        given:
        def objectUnderTest = new UnderChoosingTermEventController(commandGatewayMock)

        when:
        objectUnderTest.startChoosingTerm("eventId", TransitFrom.SURVEY_INTEREST)

        then:
        1 * commandGatewayMock.sendAndWait(
                TransitInterestAwareEventToUnderTermChoosingEventCommand.of(
                        EventId.of("eventId")
                )
        )
    }
}

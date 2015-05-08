package knbit.events.bc.eventproposal.web

import knbit.events.bc.eventproposal.domain.valueobjects.commands.AcceptProposalCommand
import knbit.events.bc.eventproposal.domain.valueobjects.commands.RejectProposalCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import spock.lang.Specification

/**
 * Created by novy on 07.05.15.
 */

class AcceptingRejectingProposalControllerTest extends Specification {

    def "should send AcceptProposalCommand given ACCEPTED as request body"() {

        given:
        def commandGatewayMock = Mock(CommandGateway.class)
        def objectUnderTest = new ProposalController(commandGatewayMock)

        when:
        objectUnderTest.changeProposalState(
                "andId", new ProposalStateDto(ProposalStateDto.ProposalState.ACCEPTED)
        )

        then:
        1 * commandGatewayMock.send(_ as AcceptProposalCommand)

    }

    def "should send RejectProposalCommand given REJECTED as request body"() {

        given:
        def commandGatewayMock = Mock(CommandGateway.class)
        def objectUnderTest = new ProposalController(commandGatewayMock)

        when:
        objectUnderTest.changeProposalState(
                "andId", new ProposalStateDto(ProposalStateDto.ProposalState.REJECTED)
        )

        then:
        1 * commandGatewayMock.send(_ as RejectProposalCommand)

    }
}

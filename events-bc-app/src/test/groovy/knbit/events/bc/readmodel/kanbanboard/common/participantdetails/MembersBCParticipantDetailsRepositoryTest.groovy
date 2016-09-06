package knbit.events.bc.readmodel.kanbanboard.common.participantdetails

import groovyx.net.http.RESTClient
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import spock.lang.Specification

/**
 * Created by novy on 05.11.15.
 */
class MembersBCParticipantDetailsRepositoryTest extends Specification {

    def MembersBCParticipantDetailsRepository objectUnderTest
    def RESTClient restClientMock

    def MemberId memberId

    void setup() {
        restClientMock = Mock(RESTClient)
        objectUnderTest = new MembersBCParticipantDetailsRepository(restClientMock)

        memberId = MemberId.of("anId")
    }

    def "should only take subset of provided data"() {
        when:
        def memberDetails = [
                userId   : "anId",
                firstName: "John",
                lastName : "Doe",
                email    : "john.doe@example.com",
                sex      : "male",
                age      : 26
        ]

        restClientMock.get([path: "${memberId.value()}"]) >> [data: memberDetails]

        then:
        objectUnderTest.detailsFor(memberId) == [
                userId   : "anId",
                firstName: "John",
                lastName : "Doe",
                email    : "john.doe@example.com",
        ]
    }
}

package knbit.events.bc.readmodel.kanbanboard.common.participantdetails

import groovyx.net.http.RESTClient
import knbit.events.bc.enrollment.domain.valueobjects.MemberId

/**
 * Created by novy on 05.11.15.
 */
class MembersBCParticipantDetailsRepository implements ParticipantDetailsRepository {

    def RESTClient client

    MembersBCParticipantDetailsRepository(RESTClient client) {
        this.client = client
    }

    @Override
    def detailsFor(MemberId memberId) {
        def serverResponse = client.get(path: "${memberId.value()}")
        takeSubsetFrom serverResponse.data
    }

    private static def takeSubsetFrom(details) {
        def keys = ['userId', 'firstName', 'lastName', 'email']
        details.subMap keys
    }
}

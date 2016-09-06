package knbit.events.bc.readmodel.kanbanboard.common.participantdetails

import knbit.events.bc.enrollment.domain.valueobjects.MemberId

/**
 * Created by novy on 05.11.15.
 */
class MockParticipantDetailsRepository implements ParticipantDetailsRepository {

    @Override
    def detailsFor(MemberId memberId) {
        [userId: memberId.value(), firstName: 'John', lastName: 'Doe']
    }
}

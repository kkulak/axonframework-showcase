package knbit.events.bc.readmodel.kanbanboard.common.participantdetails

import knbit.events.bc.enrollment.domain.valueobjects.MemberId

/**
 * Created by novy on 05.10.15.
 */

trait ParticipantDetailsRepository {

    abstract def detailsFor(MemberId memberId)
}

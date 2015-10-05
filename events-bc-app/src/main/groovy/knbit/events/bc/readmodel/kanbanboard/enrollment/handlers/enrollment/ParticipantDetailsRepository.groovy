package knbit.events.bc.readmodel.kanbanboard.enrollment.handlers.enrollment

import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import org.springframework.stereotype.Component

/**
 * Created by novy on 05.10.15.
 */

@Component
class ParticipantDetailsRepository {

    // todo: implement
    def detailsFor(MemberId memberId) {
        [participantId: memberId.value(), firstName: 'John', lastName: 'Doe']
    }
}

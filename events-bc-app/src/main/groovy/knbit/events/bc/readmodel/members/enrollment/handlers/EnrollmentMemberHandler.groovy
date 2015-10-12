package knbit.events.bc.readmodel.members.enrollment.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.TermId
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.enrollment.domain.valueobjects.events.EnrollmentEvents
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 10.10.15.
 */

@Component
class EnrollmentMemberHandler {

    def DBCollection enrollmentEvents
    def DBCollection enrollmentParticipants

    @Autowired
    EnrollmentMemberHandler(@Qualifier("enrollment-events") DBCollection enrollmentEvents,
                            @Qualifier("enrollment-participants") DBCollection enrollmentParticipants) {
        this.enrollmentEvents = enrollmentEvents
        this.enrollmentParticipants = enrollmentParticipants
    }

    @EventHandler
    def on(EnrollmentEvents.ParticipantEnrolledForTerm event) {
        changeParticipantsCountBy(event.eventId(), event.termId(), 1)
        saveMemberPreferences(event.eventId(), event.memberId(), event.termId())
    }

    private def saveMemberPreferences(EventId eventId, MemberId memberId, TermId termId) {
        enrollmentParticipants.insert(
                [eventId: eventId.value(), memberId: memberId.value(), termId: termId.value()]
        )
    }

    @EventHandler
    def on(EnrollmentEvents.ParticipantDisenrolledFromTerm event) {
        changeParticipantsCountBy(event.eventId(), event.termId(), -1)
        removeParticipantPreferences(event.eventId(), event.memberId())
    }

    private def removeParticipantPreferences(EventId eventId, MemberId memberId) {
        enrollmentParticipants.remove([eventId: eventId.value(), memberId: memberId.value()])
    }

    private def changeParticipantsCountBy(EventId eventId, TermId termId, int changeBy) {
        enrollmentEvents.update(
                [eventId: eventId.value(), 'terms.termId': termId.value()],
                [$inc: ['terms.$.participantsEnrolled': changeBy]]
        )
    }
}

package knbit.events.bc.readmodel.members.enrollment.handlers

import com.mongodb.DBCollection
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer
import knbit.events.bc.enrollment.domain.valueobjects.events.TermModifyingEvents
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 05.10.15.
 */

//todo COPIED 1:1. I WILL BURN IN HELL IF I DON'T REFACTOR IT LATER
//todo extract it as soon as we change 'domainId' to 'memberId' so it could be reused
@Component
class ModifyingTermMemberEventHandler {

    def DBCollection enrollmentCollection

    @Autowired
    ModifyingTermMemberEventHandler(@Qualifier("enrollment-events") DBCollection enrollmentCollection) {
        this.enrollmentCollection = enrollmentCollection
    }

    @EventHandler
    def on(TermModifyingEvents.LecturerAssigned event) {
        def eventId = event.eventId()
        def termId = event.termId()
        def lecturer = event.lecturer()

        enrollmentCollection.update(
                [eventId: eventId.value(), 'terms.termId': termId.value()],
                [$set: ['terms.$.lecturer': lecturerDataFrom(lecturer)]]
        )
    }

    @EventHandler
    def on(TermModifyingEvents.ParticipantLimitSet event) {
        def eventId = event.eventId()
        def termId = event.termId()
        def participantsLimit = event.participantsLimit()

        enrollmentCollection.update(
                [eventId: eventId.value(), 'terms.termId': termId.value()],
                [$set: ['terms.$.participantsLimit': participantsLimit.value()]]
        )
    }

    private static def lecturerDataFrom(Lecturer lecturer) {
        [
                firstName: lecturer.firstName(),
                lastName : lecturer.lastName()
        ]
    }
}

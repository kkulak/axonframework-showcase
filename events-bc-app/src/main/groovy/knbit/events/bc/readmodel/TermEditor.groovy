package knbit.events.bc.readmodel

import com.mongodb.DBCollection
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer
import knbit.events.bc.enrollment.domain.valueobjects.events.TermModifyingEvents

/**
 * Created by novy on 12.10.15.
 */
class TermEditor implements QueryForTerm {

    def DBCollection collection

    TermEditor(DBCollection collection) {
        this.collection = collection
    }

    def handleLecturerAssigned(TermModifyingEvents.LecturerAssigned event) {
        def eventId = event.eventId()
        def termId = event.termId()
        def lecturer = event.lecturer()

        collection.update(
                queryFor(eventId, termId),
                [$set: ['terms.$.lecturer': lecturerDataFrom(lecturer)]]
        )
    }

    def handleParticipantLimitSet(TermModifyingEvents.ParticipantLimitSet event) {
        def eventId = event.eventId()
        def termId = event.termId()
        def participantsLimit = event.participantsLimit()

        collection.update(
                queryFor(eventId, termId),
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

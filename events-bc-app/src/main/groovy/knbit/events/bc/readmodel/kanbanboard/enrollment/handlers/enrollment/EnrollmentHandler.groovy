package knbit.events.bc.readmodel.kanbanboard.enrollment.handlers.enrollment

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.TermId
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.events.EnrollmentEvents
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 05.10.15.
 */

@Component
class EnrollmentHandler {

    def DBCollection enrollmentCollection

    @Autowired
    EnrollmentHandler(@Qualifier("enrollment") DBCollection enrollmentCollection) {
        this.enrollmentCollection = enrollmentCollection
    }

    @EventHandler
    def on(EnrollmentEvents.ParticipantEnrolledForTerm event) {

    }

    @EventHandler
    def on(EnrollmentEvents.ParticipantDisenrolledFromTerm event) {
        def eventId = event.eventId()
        def termId = event.termId()
        def participantId = event.memberId()

        enrollmentCollection.update(
                query(eventId, termId), [
                $pull: [
                        'terms.$.participants': [participantId: participantId.value()]
                ]
        ])
    }

//    todo duplication
    private static def query(EventId eventId, TermId termId) {
        [
                domainId      : eventId.value(),
                'terms.termId': termId.value()
        ]
    }
}

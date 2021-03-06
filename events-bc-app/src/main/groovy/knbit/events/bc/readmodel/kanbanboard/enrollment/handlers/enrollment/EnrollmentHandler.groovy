package knbit.events.bc.readmodel.kanbanboard.enrollment.handlers.enrollment

import com.mongodb.DBCollection
import knbit.events.bc.enrollment.domain.valueobjects.events.EnrollmentEvents
import knbit.events.bc.readmodel.QueryForTerm
import knbit.events.bc.readmodel.kanbanboard.common.participantdetails.ParticipantDetailsRepository
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 05.10.15.
 */

@Component
class EnrollmentHandler implements QueryForTerm {

    def DBCollection enrollmentCollection
    def ParticipantDetailsRepository detailsRepository

    @Autowired
    EnrollmentHandler(@Qualifier("enrollment") DBCollection enrollmentCollection,
                      ParticipantDetailsRepository detailsRepository) {

        this.enrollmentCollection = enrollmentCollection
        this.detailsRepository = detailsRepository
    }

    @EventHandler
    def on(EnrollmentEvents.ParticipantEnrolledForTerm event) {
        def eventId = event.eventId()
        def termId = event.termId()

        def participantId = event.memberId()
        def participantDetails = detailsRepository.detailsFor(participantId)

        enrollmentCollection.update(
                queryFor(eventId, termId), [
                $push: ['terms.$.participants': participantDetails]
        ])
    }

    @EventHandler
    def on(EnrollmentEvents.ParticipantDisenrolledFromTerm event) {
        def eventId = event.eventId()
        def termId = event.termId()
        def participantId = event.memberId()

        enrollmentCollection.update(
                queryFor(eventId, termId), [
                $pull: [
                        'terms.$.participants': [userId: participantId.value()]
                ]
        ])
    }
}

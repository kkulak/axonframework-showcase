package knbit.events.bc.readmodel.members.enrollment

import com.mongodb.DBCollection
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 11.10.15.
 */

@Component
class MemberEnrollmentQuery {

    def DBCollection enrollmentEventCollection
    def DBCollection enrollmentParticipantCollection

    @Autowired
    MemberEnrollmentQuery(@Qualifier('enrollment-events') DBCollection enrollmentEventCollection,
                          @Qualifier('enrollment-participants') DBCollection enrollmentParticipantCollection) {
        this.enrollmentEventCollection = enrollmentEventCollection
        this.enrollmentParticipantCollection = enrollmentParticipantCollection
    }

    def queryFor(MemberId memberId) {
        allEvents()
                .collect { addUserPreferencesTo(it, memberId) }
    }

    private def allEvents() {
        enrollmentEventCollection.find().toArray()
    }

    private def addUserPreferencesTo(eventWithoutPreferences, memberId) {
        def optionalTermId = possiblyPreferredTerm(eventWithoutPreferences.eventId, memberId.value())

        def memberEnrolledForTerm = {
            term -> optionalTermId.map({ it == term.termId }).orElse(false)
        }

        def termsWithEnrollmentInformationAssigned =
                eventWithoutPreferences.terms
                        .collect { it + [enrolled: memberEnrolledForTerm(it)] }


        def preferences = {
            def terms = [terms: termsWithEnrollmentInformationAssigned]
            optionalTermId.map({ [chosenTerm: it] + terms }).orElse(terms)
        }

        eventWithoutPreferences + preferences()
    }

    private def Optional<String> possiblyPreferredTerm(eventId, memberId) {
        def possibleUserPreference = enrollmentParticipantCollection.findOne([eventId: eventId, memberId: memberId])

        Optional.ofNullable(possibleUserPreference)
                .map({ it.termId })
    }
}

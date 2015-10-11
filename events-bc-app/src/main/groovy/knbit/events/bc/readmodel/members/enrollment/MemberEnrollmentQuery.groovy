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
        def termIdOrNull = preferredTermOrNull(eventWithoutPreferences.eventId, memberId.value())
        def termsWithEnrollmentInformationAssigned =
                eventWithoutPreferences
                        .terms
                        .collect { it + [enrolled: it.termId == termIdOrNull] }

        def preferences = [terms: termsWithEnrollmentInformationAssigned]
        if (termIdOrNull) {
            preferences.chosenTerm = termIdOrNull
        }

        eventWithoutPreferences + preferences
    }

    private def preferredTermOrNull(eventId, memberId) {
        def possibleUserPreference = enrollmentParticipantCollection.findOne([eventId: eventId, memberId: memberId])
        possibleUserPreference ? possibleUserPreference.termId : null
    }
}

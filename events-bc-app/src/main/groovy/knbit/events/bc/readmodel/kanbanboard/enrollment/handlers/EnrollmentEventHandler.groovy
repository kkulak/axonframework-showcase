package knbit.events.bc.readmodel.kanbanboard.enrollment.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.IdentifiedTerm
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 05.10.15.
 */

@Component
class EnrollmentEventHandler {

    def DBCollection enrollmentCollection

    @Autowired
    EnrollmentEventHandler(@Qualifier("enrollment") DBCollection enrollmentCollection) {
        this.enrollmentCollection = enrollmentCollection
    }

    @EventHandler
    def on(EventUnderEnrollmentEvents.Created event) {
        def eventId = event.eventId()
        def eventDetails = event.eventDetails()
        def identifiedTerms = event.terms()

        enrollmentCollection.insert([
                domainId      : eventId.value(),
                name          : eventDetails.name().value(),
                description   : eventDetails.description().value(),
                eventType     : eventDetails.type(),
                eventFrequency: eventDetails.frequency(),
                terms         : termsDataFrom(identifiedTerms)
        ])
    }

    private static def termsDataFrom(Collection<IdentifiedTerm> identifiedTerms) {
        identifiedTerms.collect { identifiedTerm ->
            [
                    termId      : identifiedTerm.termId().value(),
                    date        : identifiedTerm.duration().start(),
                    duration    : identifiedTerm.duration().duration().getStandardMinutes(),
                    capacity    : identifiedTerm.capacity().value(),
                    location    : identifiedTerm.location().value(),
                    participants: []
            ]
        }
    }
}

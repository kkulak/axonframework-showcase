package knbit.events.bc.readmodel.members.enrollment.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.IdentifiedTerm
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents
import knbit.events.bc.readmodel.EventDetailsWrapper
import knbit.events.bc.readmodel.TermWrapper
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 11.10.15.
 */

@Component
class OnEnrollmentCreationHandler {

    def DBCollection enrollmentEventsCollection

    @Autowired
    OnEnrollmentCreationHandler(@Qualifier("enrollment-events") DBCollection enrollmentEventsCollection) {
        this.enrollmentEventsCollection = enrollmentEventsCollection
    }

    @EventHandler
    def on(EventUnderEnrollmentEvents.Created event) {
        def eventId = [eventId: event.eventId().value()]
        def eventDetails = EventDetailsWrapper.asMap(event.eventDetails())
        def terms = termDataFrom(event.terms())

        enrollmentEventsCollection.insert(eventId + eventDetails + terms)
    }

    private static def termDataFrom(Collection<IdentifiedTerm> terms) {
        [terms: terms.collect { TermWrapper.asMap(it) + [participantsEnrolled: 0] }]
    }
}
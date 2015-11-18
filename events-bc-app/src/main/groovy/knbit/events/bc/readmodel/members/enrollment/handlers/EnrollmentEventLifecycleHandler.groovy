package knbit.events.bc.readmodel.members.enrollment.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.EnrollmentIdentifiedTerm
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents
import knbit.events.bc.readmodel.EventDetailsWrapper
import knbit.events.bc.readmodel.RemoveEventRelatedData
import knbit.events.bc.readmodel.TermWrapper
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 11.10.15.
 */

@Component
class EnrollmentEventLifecycleHandler implements RemoveEventRelatedData {

    def DBCollection enrollmentEventsCollection

    @Autowired
    EnrollmentEventLifecycleHandler(@Qualifier("enrollment-events") DBCollection enrollmentEventsCollection) {
        this.enrollmentEventsCollection = enrollmentEventsCollection
    }

    @EventHandler
    def on(EventUnderEnrollmentEvents.Created event) {
        def eventId = [eventId: event.eventId().value()]
        def eventDetails = EventDetailsWrapper.asMap(event.eventDetails())
        def terms = termDataFrom(event.terms())

        enrollmentEventsCollection.insert(eventId + eventDetails + terms)
    }

    @EventHandler
    def on(EventUnderEnrollmentEvents.TransitedToReady event) {
        removeDataBy(event.eventId()).from(enrollmentEventsCollection)
    }

    private static def termDataFrom(Collection<EnrollmentIdentifiedTerm> terms) {
        [terms: terms.collect { TermWrapper.asMap(it) + [participantsEnrolled: 0] }]
    }
}
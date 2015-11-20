package knbit.events.bc.readmodel.kanbanboard.enrollment.handlers

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
 * Created by novy on 05.10.15.
 */

@Component
class EventUnderEnrollmentEventHandler implements RemoveEventRelatedData {

    def DBCollection enrollmentCollection

    @Autowired
    EventUnderEnrollmentEventHandler(@Qualifier("enrollment") DBCollection enrollmentCollection) {
        this.enrollmentCollection = enrollmentCollection
    }

    @EventHandler
    def on(EventUnderEnrollmentEvents.Created event) {
        def eventId = [eventId: event.eventId().value()]
        def eventDetails = EventDetailsWrapper.asMap(event.eventDetails())
        def terms = termsDataFrom(event.terms())

        enrollmentCollection.insert(eventId + eventDetails + terms)
    }

    @EventHandler
    def on(EventUnderEnrollmentEvents.TransitedToReady event) {
        removeDataBy(event.eventId()).from(enrollmentCollection)
    }

    private static def termsDataFrom(Collection<EnrollmentIdentifiedTerm> identifiedTerms) {
        [terms: identifiedTerms.collect { TermWrapper.asMap(it) + [participants: []] }]
    }
}

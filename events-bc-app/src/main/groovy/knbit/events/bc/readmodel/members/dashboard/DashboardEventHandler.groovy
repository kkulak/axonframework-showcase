package knbit.events.bc.readmodel.members.dashboard

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.EventDetails
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.IdentifiedTermWithAttendees
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents
import knbit.events.bc.readmodel.EventDetailsWrapper
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 20.10.15.
 */

@Component
class DashboardEventHandler {

    def DBCollection collection

    @Autowired
    DashboardEventHandler(@Qualifier("dashboard-events") DBCollection collection) {
        this.collection = collection
    }

    @EventHandler
    def on(ReadyEvents.Created event) {
        collection << denormalizedEvents(event.eventId(), event.eventDetails(), event.terms())
    }

    static def denormalizedEvents(EventId eventId,
                                  EventDetails details,
                                  Collection<IdentifiedTermWithAttendees> terms) {
        terms.collect {
            [eventId: eventId.value()] + EventDetailsWrapper.asMap(details) + termDataFrom(it)
        }
    }

    static def termDataFrom(IdentifiedTermWithAttendees term) {
        [
                termId           : term.termId().value(),
                start            : term.duration().start(),
                end              : term.duration().end(),
                participantsLimit: term.limit().value(),
                location         : term.location().value(),
                lecturer         : [
                        firstName: term.lecturer().firstName(),
                        lastName : term.lecturer().lastName()
                ],
                attendees        : term.attendees().collect { it.memberId().value() }
        ]
    }
}

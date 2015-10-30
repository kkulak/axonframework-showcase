package knbit.events.bc.readmodel.members.dashboard

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.Attendee
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails
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
        def eventId = [eventId: event.eventId().value()]
        def eventDetails = detailsDataFrom(event.eventDetails())
        def attendees = attendeesDataFrom(event.attendees())

        collection << eventId + eventDetails + attendees
    }

    private static def detailsDataFrom(EventReadyDetails details) {
        def termRelatedData = [
                start            : details.duration().start(),
                end              : details.duration().end(),
                participantsLimit: details.limit().value(),
                location         : details.location().value(),
                lecturer         : [
                        firstName: details.lecturer().firstName(),
                        lastName : details.lecturer().lastName()
                ]
        ]

        EventDetailsWrapper.asMap(details.eventDetails()) + termRelatedData
    }

    private static def attendeesDataFrom(Collection<Attendee> attendees) {
        [attendees: attendees.collect { it.memberId().value() }]
    }
}

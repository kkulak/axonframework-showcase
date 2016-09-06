package knbit.events.bc.readmodel.members.dashboard

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.Attendee
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents
import knbit.events.bc.readmodel.EventDetailsWrapper
import knbit.events.bc.readmodel.RemoveEventRelatedData
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

import static knbit.events.bc.readmodel.TermWrapper.lecturersOf

/**
 * Created by novy on 20.10.15.
 */

@Component
class DashboardEventHandler implements RemoveEventRelatedData {

    def DBCollection collection

    @Autowired
    DashboardEventHandler(@Qualifier("dashboard-events") DBCollection collection) {
        this.collection = collection
    }

    @EventHandler
    def on(ReadyEvents.Created event) {
        def eventId = [eventId: event.readyEventId().value()]
        def eventDetails = detailsDataFrom(event.eventDetails())
        def attendees = attendeesDataFrom(event.attendees())

        collection << eventId + eventDetails + attendees
    }

    @EventHandler
    def on(ReadyEvents.DetailsChanged event) {
        def queryById = [eventId: event.readyEventId().value()]
        def detailsAsMap = detailsDataFrom(event.newDetails())

        collection.update(queryById, [$set: detailsAsMap])
    }

    @EventHandler
    def on(ReadyEvents.Cancelled evt) {
        removeDataBy(evt.eventId()).from(collection)
    }

    private static def detailsDataFrom(EventReadyDetails details) {
        def termRelatedData = [
                start            : details.duration().start(),
                end              : details.duration().end(),
                participantsLimit: details.limit().value(),
                location         : details.location().value(),
                lecturers        : lecturersOf(details.lecturers())
        ]

        EventDetailsWrapper.asMap(details.eventDetails()) + termRelatedData
    }

    private static def attendeesDataFrom(Collection<Attendee> attendees) {
        [attendees: attendees.collect { it.memberId().value() }]
    }
}

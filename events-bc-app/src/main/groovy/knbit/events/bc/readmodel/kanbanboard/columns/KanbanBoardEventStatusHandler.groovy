package knbit.events.bc.readmodel.kanbanboard.columns

import com.mongodb.DBCollection
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventEvents
import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventEvents.EventDetailsChanged
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermStatusEvents
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents
import knbit.events.bc.common.domain.valueobjects.EventCancelled
import knbit.events.bc.common.domain.valueobjects.EventDetails
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.common.readmodel.EventStatus
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents
import knbit.events.bc.readmodel.RemoveEventRelatedData
import knbit.events.bc.readmodel.TermWrapper
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

import static knbit.events.bc.common.readmodel.EventStatus.*
import static knbit.events.bc.readmodel.EventDetailsWrapper.sectionOrNull
import static knbit.events.bc.readmodel.EventDetailsWrapper.urlOrNull

@Component
class KanbanBoardEventStatusHandler implements RemoveEventRelatedData {
    def DBCollection collection

    @Autowired
    KanbanBoardEventStatusHandler(@Qualifier("kanban-board") collection) {
        this.collection = collection
    }

    @EventHandler
    def on(BacklogEventEvents.Created event) {
        def eventId = [eventId: event.eventId().value()]
        def detailsMap = detailsAsMap(event.eventDetails())
        def statusData = [
                eventStatus    : BACKLOG,
                reachableStatus: [BACKLOG, SURVEY_INTEREST, CHOOSING_TERM]
        ]

        collection.insert(eventId + detailsMap + statusData)
    }

    @EventHandler
    def on(EventDetailsChanged event) {
        def queryById = [eventId: event.eventId().value()]
        def newDetailsAsMap = detailsAsMap(event.newDetails())

        collection.update(queryById, [$set: newDetailsAsMap])
    }

    private def detailsAsMap(EventDetails details) {
        return [
                name     : details.name().value(),
                eventType: details.type(),
                imageUrl : urlOrNull(details.imageUrl()),
                section  : sectionOrNull(details.section())
        ]
    }

    @EventHandler
    def on(InterestAwareEvents.Created event) {
        updateEventStatus(event.eventId(), SURVEY_INTEREST, [SURVEY_INTEREST, CHOOSING_TERM])
    }

    @EventHandler
    def on(UnderChoosingTermEventEvents.Created event) {
        updateEventStatus(event.eventId(), CHOOSING_TERM, [CHOOSING_TERM])
    }

    @EventHandler
    def on(TermStatusEvents.Ready event) {
        updateEventStatus(event.eventId(), CHOOSING_TERM, [CHOOSING_TERM, ENROLLMENT])
    }

    @EventHandler
    def on(TermStatusEvents.Pending event) {
        updateEventStatus(event.eventId(), CHOOSING_TERM, [CHOOSING_TERM])
    }

    @EventHandler
    def on(EventUnderEnrollmentEvents.Created event) {
        updateEventStatus(event.eventId(), ENROLLMENT, [ENROLLMENT, READY])
    }


    @EventHandler
    def on(EventUnderEnrollmentEvents.TransitedToReady event) {
        removeDataBy(event.eventId()).from(collection)
    }

    @EventHandler
    def on(ReadyEvents.Created event) {
        def eventId = [eventId: event.readyEventId().value()]
        def detailsAsMap = eventReadyDetailsFrom(event.eventDetails())
        def statusData = [eventStatus: READY, reachableStatus: [READY]]

        collection.insert(eventId + detailsAsMap + statusData)
    }

    @EventHandler
    def on(ReadyEvents.DetailsChanged event) {
        def queryById = [eventId: event.readyEventId().value()]
        def detailsAsMap = eventReadyDetailsFrom(event.newDetails())

        collection.update(queryById, [$set: detailsAsMap])
    }

    private static def eventReadyDetailsFrom(EventReadyDetails details) {
        return [
                name     : details.name().value(),
                eventType: details.type(),
                imageUrl : urlOrNull(details.imageUrl()),
                section  : sectionOrNull(details.section()),
                start    : details.duration().start(),
                location : details.location().value(),
                lecturers: TermWrapper.lecturersOf(details.lecturers())
        ]
    }

    @EventHandler
    def on(ReadyEvents.TookPlace event) {
        removeDataBy(event.readyEventId()).from(collection)
    }

    @EventHandler
    def on(EventCancelled event) {
        removeDataBy(event.eventId()).from(collection)
    }

    private def updateEventStatus(EventId eventId,
                                  EventStatus currentStatus,
                                  List<EventStatus> reachableStatus) {

        collection.update(
                [eventId: eventId.value()],
                [
                        $set: [
                                reachableStatus: reachableStatus,
                                eventStatus    : currentStatus
                        ]
                ]
        )
    }
}


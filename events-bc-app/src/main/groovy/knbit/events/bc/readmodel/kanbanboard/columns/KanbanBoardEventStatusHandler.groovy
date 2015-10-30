package knbit.events.bc.readmodel.kanbanboard.columns

import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventEvents
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermStatusEvents
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.common.readmodel.EventStatus
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents
import knbit.events.bc.readmodel.RemoveEventRelatedData
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

import static knbit.events.bc.common.readmodel.EventStatus.*

@Component
class KanbanBoardEventStatusHandler implements RemoveEventRelatedData {
    def collection

    @Autowired
    KanbanBoardEventStatusHandler(@Qualifier("kanban-board") collection) {
        this.collection = collection
    }

    @EventHandler
    def on(BacklogEventEvents.Created event) {
        def eventId = event.eventId()
        def details = event.eventDetails()

        collection.insert([
                eventId        : eventId.value(),
                name           : details.name().value(),
                eventType      : details.type(),
                eventStatus    : BACKLOG,
                reachableStatus: [BACKLOG, SURVEY_INTEREST, CHOOSING_TERM]
        ])
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
        def eventId = event.readyEventId()
        def details = event.eventDetails()

        // todo: location, lecturer ?
        collection.insert([
                eventId        : eventId.value(),
                name           : details.name().value(),
                eventType      : details.type(),
                eventStatus    : READY,
                reachableStatus: [READY]
        ])
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


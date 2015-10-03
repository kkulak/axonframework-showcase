package knbit.events.bc.readmodel.kanbanboard.columns

import knbit.events.bc.backlogevent.domain.valueobjects.events.BacklogEventEvents
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermStatusEvents
import knbit.events.bc.choosingterm.domain.valuobjects.events.UnderChoosingTermEventEvents
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

import static knbit.events.bc.common.readmodel.EventStatus.*

@Component
class KanbanBoardEventStatusHandler {
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
                eventDomainId  : eventId.value(),
                name           : details.name().value(),
                eventType      : details.type(),
                eventFrequency : details.frequency(),
                eventStatus    : BACKLOG,
                reachableStatus: [BACKLOG, SURVEY_INTEREST, CHOOSING_TERM]
        ])
    }

    @EventHandler
    def on(InterestAwareEvents.Created event) {
        collection.update(
                [eventDomainId: event.eventId().value()],
                [$set: [
                        reachableStatus: [SURVEY_INTEREST, CHOOSING_TERM],
                        eventStatus    : SURVEY_INTEREST
                ]
                ]
        )
    }

    @EventHandler
    def on(UnderChoosingTermEventEvents.Created event) {
        collection.update(
                [eventDomainId: event.eventId().value()],
                [$set: [
                        reachableStatus: [CHOOSING_TERM],
                        eventStatus    : CHOOSING_TERM
                ]
                ]
        )
    }

    @EventHandler
    def on(TermStatusEvents.Ready event) {
        collection.update(
                [eventDomainId: event.eventId().value()],
                [$set: [
                        reachableStatus: [CHOOSING_TERM, ENROLLMENT],
                        eventStatus    : CHOOSING_TERM]
                ]
        )
    }

    @EventHandler
    def on(TermStatusEvents.Pending event) {
        collection.update(
                [eventDomainId: event.eventId().value()],
                [$set: [
                        reachableStatus: [CHOOSING_TERM],
                        eventStatus    : CHOOSING_TERM]
                ]
        )
    }

    @EventHandler
    def on(EventUnderEnrollmentEvents.Created event) {
        collection.update(
                [eventDomainId: event.eventId().value()],
                [$set: [
                        reachableStatus: [ENROLLMENT, READY],
                        eventStatus    : ENROLLMENT]
                ]
        )
    }

}

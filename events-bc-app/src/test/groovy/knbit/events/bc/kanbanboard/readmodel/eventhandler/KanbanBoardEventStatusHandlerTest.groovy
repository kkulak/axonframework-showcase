package knbit.events.bc.kanbanboard.readmodel.eventhandler

import knbit.events.bc.backlogevent.domain.builders.BacklogEventCreatedBuilder
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.interest.builders.EventDetailsBuilder

import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents
import knbit.events.bc.kanbanboard.readmodel.KanbanBoardContextConfiguration
import knbit.events.bc.kanbanboard.readmodel.model.KanbanBoard
import knbit.events.bc.kanbanboard.readmodel.repository.KanbanBoardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static knbit.events.bc.common.domain.enums.EventFrequency.ONE_OFF
import static knbit.events.bc.common.domain.enums.EventType.LECTURE
import static knbit.events.bc.common.readmodel.EventStatus.BACKLOG
import static knbit.events.bc.common.readmodel.EventStatus.CHOOSING_TERM
import static knbit.events.bc.common.readmodel.EventStatus.SURVEY_INTEREST

@ContextConfiguration(loader = SpringApplicationContextLoader.class,
        classes = [KanbanBoardContextConfiguration.class])
@IntegrationTest
class KanbanBoardEventStatusHandlerTest extends Specification {
    @Autowired
    def KanbanBoardRepository repository;
    @Autowired
    def KanbanBoardEventStatusHandler handler;

    void setup() {
        repository.deleteAll()
    }

    def "should save kanbanboard entity on handling BacklogEventCreated"() {
        given:
        def event = BacklogEventCreatedBuilder.instance().build()

        when:
        handler.handle(event)

        then:
        def persistentEvent = repository.findByEventDomainId(event.eventId().value())
        persistentEvent.eventDomainId == event.eventId().value()
        persistentEvent.eventFrequency == event.eventDetails().frequency()
        persistentEvent.eventStatus == BACKLOG
        persistentEvent.eventType == LECTURE
        persistentEvent.name == event.eventDetails().name().value()
        persistentEvent.reachableStatus == [BACKLOG, SURVEY_INTEREST, CHOOSING_TERM]
    }

    def "should update kanbanboard entity on handling EventStatusAware"() {
        given:
        repository.save(new KanbanBoard("id", "name", LECTURE, ONE_OFF, BACKLOG, [BACKLOG, SURVEY_INTEREST, CHOOSING_TERM]))
        def event = InterestAwareEvents.Created.of(EventId.of("id"), EventDetailsBuilder.instance().build())

        when:
        handler.handle(event)

        then:
        def persistentEvent = repository.findByEventDomainId(event.eventId().value())
        persistentEvent.eventDomainId == event.eventId().value()
        persistentEvent.eventFrequency == event.eventDetails().frequency()
        persistentEvent.eventStatus == SURVEY_INTEREST
        persistentEvent.eventType == LECTURE
        persistentEvent.name == event.eventDetails().name().value()
        persistentEvent.reachableStatus == [SURVEY_INTEREST, CHOOSING_TERM]
    }

}

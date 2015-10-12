package knbit.events.bc.readmodel.kanbanboard.enrollment.handlers

import com.mongodb.DBCollection
import knbit.events.bc.enrollment.domain.valueobjects.events.TermModifyingEvents
import knbit.events.bc.readmodel.TermEditor
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 05.10.15.
 */

@Component
class EventMasterReadModelTermEventHandler {

    def TermEditor termHandler

    @Autowired
    EventMasterReadModelTermEventHandler(@Qualifier("enrollment") DBCollection enrollmentCollection) {
        termHandler = new TermEditor(enrollmentCollection)
    }

    @EventHandler
    def on(TermModifyingEvents.LecturerAssigned event) {
        termHandler.handleLecturerAssigned(event)
    }

    @EventHandler
    def on(TermModifyingEvents.ParticipantLimitSet event) {
        termHandler.handleParticipantLimitSet(event)
    }
}

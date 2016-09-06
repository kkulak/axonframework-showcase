package knbit.events.bc.readmodel.kanbanboard.interest.handlers

import com.mongodb.DBCollection
import knbit.events.bc.interest.domain.valueobjects.events.SurveyEvents
import knbit.events.bc.interest.domain.valueobjects.events.surveystarting.SurveyStartingEvents
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 04.06.15.
 */

@Component
class SurveyEventHandler {

    def DBCollection collection

    @Autowired
    SurveyEventHandler(@Qualifier("survey") DBCollection collection) {
        this.collection = collection
    }

    @EventHandler
    def on(SurveyStartingEvents.Started event) {
        def eventId = event.eventId().value()

        startVoting(eventId)
    }

    @EventHandler
    def on(SurveyEvents.VotedUp event) {
        def eventId = event.eventId()
        voteUp(eventId.value())
    }

    @EventHandler
    def on(SurveyEvents.VotedDown event) {
        def eventId = event.eventId()
        voteDown(eventId.value())
    }

    private def startVoting(eventId) {
        collection.update(
                [eventId: eventId],
                [$set: [votedUp: 0, votedDown: 0]]
        )
    }

    private def voteUp(eventId) {
        collection.update(
                [eventId: eventId],
                [$inc: [votedUp: 1]]
        )
    }

    private def voteDown(eventId) {
        collection.update(
                [eventId: eventId],
                [$inc: [votedDown: 1]]
        )
    }
}
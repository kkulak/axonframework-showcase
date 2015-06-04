package knbit.events.bc.interest.viewmodel.eventmaster.handlers

import com.mongodb.DBCollection
import knbit.events.bc.interest.domain.valueobjects.events.SurveyVotedDownEvent
import knbit.events.bc.interest.domain.valueobjects.events.SurveyVotedUpEvent
import knbit.events.bc.interest.domain.valueobjects.events.surveystarting.SurveyingInterestStartedEvent
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

/**
 * Created by novy on 04.06.15.
 */

@Component
class SurveyEventHandler {

    def DBCollection collection

    SurveyEventHandler(DBCollection collection) {
        this.collection = collection
    }

    @EventHandler
    def on(SurveyingInterestStartedEvent event) {
        def domainId = event.eventId().value()

        startVoting(domainId)
    }

    @EventHandler
    def on(SurveyVotedUpEvent event) {
        def eventId = event.eventId()
        voteUp(eventId.value())
    }

    @EventHandler
    def on(SurveyVotedDownEvent event) {
        def eventId = event.eventId()
        voteDown(eventId.value())
    }

    private def startVoting(domainId) {
        collection.update(
                [domainId: domainId],
                [$set: [votedUp: 0, votedDown: 0]]
        )
    }

    private def voteUp(domainId) {
        collection.update(
                [domainId: domainId],
                [$inc: [votedUp: 1]]
        )
    }

    private def voteDown(domainId) {
        collection.update(
                [domainId: domainId],
                [$inc: [votedDown: 1]]
        )
    }
}
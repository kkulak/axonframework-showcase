package knbit.events.bc.readmodel.members.surveypreview.handlers

import com.mongodb.DBCollection
import knbit.events.bc.interest.domain.valueobjects.events.SurveyEvents
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier

/**
 * Created by novy on 10.10.15.
 */
class InterestVotingHandler {

    def DBCollection collection

    @Autowired
    InterestVotingHandler(@Qualifier("survey-votes") DBCollection collection) {
        this.collection = collection
    }

    @EventHandler
    def on(SurveyEvents.VotedUp event) {

    }

    @EventHandler
    def on(SurveyEvents.VotedDown event) {

    }
}

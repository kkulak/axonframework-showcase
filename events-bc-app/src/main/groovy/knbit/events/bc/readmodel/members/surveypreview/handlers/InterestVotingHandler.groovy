package knbit.events.bc.readmodel.members.surveypreview.handlers

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.interest.domain.valueobjects.events.SurveyEvents
import knbit.events.bc.readmodel.members.surveypreview.VoteType
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
        collection.insert(
                newEntryWithVote(event.eventId(), event.attendee().memberId(), VoteType.POSITIVE)
        )
    }

    @EventHandler
    def on(SurveyEvents.VotedDown event) {
        collection.insert(
                newEntryWithVote(event.eventId(), event.attendee().memberId(), VoteType.NEGATIVE)
        )
    }

    private static def newEntryWithVote(EventId eventId, MemberId memberId, VoteType vote) {
        [
                eventId : eventId.value(),
                memberId: memberId.value(),
                voted   : vote
        ]
    }
}


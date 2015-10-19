package knbit.events.bc.readmodel.members.surveypreview.handlers

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.interest.domain.valueobjects.events.SurveyEvents
import knbit.events.bc.readmodel.members.surveypreview.VoteType
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 10.10.15.
 */
@Component
class InterestVotingHandler {

    def DBCollection votesCollection
    def DBCollection eventsCollection

    @Autowired
    InterestVotingHandler(@Qualifier("survey-votes") DBCollection votesCollection,
                          @Qualifier("survey-events") DBCollection eventsCollection) {
        this.eventsCollection = eventsCollection
        this.votesCollection = votesCollection
    }

    @EventHandler
    def on(SurveyEvents.VotedUp event) {
        eventsCollection.update(
                [eventId: event.eventId().value()],
                [$inc: [votedUp: 1]]
        )

        votesCollection.insert(
                newEntryWithVote(event.eventId(), event.attendee().memberId(), VoteType.POSITIVE)
        )
    }

    @EventHandler
    def on(SurveyEvents.VotedDown event) {
        votesCollection.insert(
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


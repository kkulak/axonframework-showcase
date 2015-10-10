package knbit.events.bc.readmodel.members.surveypreview

import com.mongodb.DBCollection
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 10.10.15.
 */

@Component
class InterestMemberViewModelQuery {

    private final DBCollection surveyCollection
    private final DBCollection votesCollection

    @Autowired
    InterestMemberViewModelQuery(@Qualifier("survey-events") DBCollection surveyCollection,
                                 @Qualifier("survey-votes") DBCollection votesCollection) {
        this.votesCollection = votesCollection
        this.surveyCollection = surveyCollection
    }

    def queryFor(MemberId memberId) {
        allEvents()
                .collect { assignVoteIfMemberVoted(it, memberId.value()) }
    }

    private def allEvents() {
        surveyCollection.find().toArray()
    }

    private def assignVoteIfMemberVoted(event, memberId) {
        event + memberVoteOrEmptyMap(event.eventId, memberId)
    }

    private def memberVoteOrEmptyMap(eventId, memberId) {
        def memberVoteOrNull = votesCollection.findOne([eventId: eventId, memberId: memberId])
        memberVoteOrNull ? [voted: memberVoteOrNull.voted] : []
    }
}

package knbit.events.bc.readmodel.members.header

import com.mongodb.DBCollection
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 25.10.15.
 */

@Component
class MembersHeaderQuery {

    def DBCollection surveyCollection
    def DBCollection dashboardEventsCollection

    @Autowired
    MembersHeaderQuery(@Qualifier("survey-events") DBCollection surveyCollection,
                       @Qualifier("dashboard-events") DBCollection dashboardEventsCollection) {

        this.surveyCollection = surveyCollection
        this.dashboardEventsCollection = dashboardEventsCollection
    }

    def headerDataFor(MemberId memberId, DateTime date) {
        surveyInProgress() + upcomingEvents(date) + nextEvent(memberId, date)
    }

    def newestHeaderData(MemberId memberId) {
        headerDataFor(memberId, DateTime.now())
    }

    private def surveyInProgress() {
        [surveyInProgress: surveyCollection.count()]
    }

    private def upcomingEvents(DateTime notEarlierThat) {
        [upcomingEvents: dashboardEventsCollection.count([start: [$gte: notEarlierThat]])]
    }

    private def nextEvent(MemberId memberId, DateTime notEarlierThat) {
        def nextEvent = dashboardEventsCollection.findOne([
                attendees: memberId.value(),
                start: [$gte: notEarlierThat]
        ])

        [nextEvent: nextEvent ?: []]
    }
}

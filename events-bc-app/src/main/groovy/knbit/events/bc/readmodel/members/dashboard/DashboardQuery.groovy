package knbit.events.bc.readmodel.members.dashboard

import com.mongodb.DBCollection
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 21.10.15.
 */

@Component
class DashboardQuery {

    def DBCollection collection

    @Autowired
    DashboardQuery(@Qualifier("dashboard-events") DBCollection collection) {
        this.collection = collection
    }

    def all() {
        collection.find().toArray()
    }

    def allWithPreferencesFor(MemberId memberId) {
        def assignEnrolledProperty = {
            it + [enrolled: it.attendees.contains(memberId.value())]
        }

        collection
                .find()
                .toArray()
                .collect(assignEnrolledProperty)
    }
}

package knbit.events.bc.readmodel.members.dashboard

import com.mongodb.DBCollection
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

    def allWithPreferencesFor(String memberId) {
        def assignEnrolledProperty = {
            it + [enrolled: it.attendees.contains(memberId)]
        }

        collection
                .find()
                .toArray()
                .collect(assignEnrolledProperty)
    }
}

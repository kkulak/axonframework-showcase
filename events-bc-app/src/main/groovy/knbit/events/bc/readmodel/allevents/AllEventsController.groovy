package knbit.events.bc.readmodel.allevents

import com.mongodb.DBCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by novy on 29.06.15.
 */

@RestController
@RequestMapping(value = '/events')
class AllEventsController {

    def allEventsCollection

    @Autowired
    MembersInterestViewModelController(@Qualifier("all-events") DBCollection allEventsCollection) {
        this.allEventsCollection = allEventsCollection
    }

    @RequestMapping
    def allEvents() {
        allEventsCollection.find().toArray()
    }
}

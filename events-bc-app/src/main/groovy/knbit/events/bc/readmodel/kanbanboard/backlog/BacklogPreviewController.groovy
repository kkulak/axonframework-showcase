package knbit.events.bc.readmodel.kanbanboard.backlog

import com.mongodb.DBCollection
import knbit.events.bc.auth.Authorized
import knbit.events.bc.auth.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by novy on 28.11.15.
 */
@RestController
@RequestMapping("/events/")
class BacklogPreviewController {

    def DBCollection backlogCollection

    @Autowired
    BacklogPreviewController(@Qualifier("backlogReadmodel") DBCollection backlogCollection) {
        this.backlogCollection = backlogCollection
    }

    @Authorized(Role.EVENTS_MANAGEMENT)
    @RequestMapping(value = "/{eventId}/backlog", method = RequestMethod.GET)
    def enrollmentPreviewFor(@PathVariable("eventId") eventId) {
        backlogCollection.findOne([eventId: eventId])
    }
}

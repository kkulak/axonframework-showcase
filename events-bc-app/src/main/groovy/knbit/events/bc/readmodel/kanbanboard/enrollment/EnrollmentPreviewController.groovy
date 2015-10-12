package knbit.events.bc.readmodel.kanbanboard.enrollment

import com.mongodb.DBCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by novy on 05.10.15.
 */

@RestController
@RequestMapping("/events/")
class EnrollmentPreviewController {

    def DBCollection enrollmentCollection

    @Autowired
    EnrollmentPreviewController(@Qualifier("enrollment") DBCollection enrollmentCollection) {
        this.enrollmentCollection = enrollmentCollection
    }

    @RequestMapping(value = "/{eventId}/enrollment", method = RequestMethod.GET)
    def enrollmentPreviewFor(@PathVariable("eventId") eventId) {
        enrollmentCollection.findOne([eventId: eventId])
    }
}
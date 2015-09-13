package knbit.events.bc.readmodel.kanbanboard.choosingterm

import com.mongodb.DBCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by novy on 01.09.15.
 */

@RestController
@RequestMapping("/events/")
class ChoosingTermPreviewController {

    def DBCollection termsCollection

    @Autowired
    ChoosingTermPreviewController(@Qualifier("choosing-term") DBCollection termsCollection) {
        this.termsCollection = termsCollection
    }

    @RequestMapping(value = "/{eventId}/choosing-term", method = RequestMethod.GET)
    def termsPreviewFor(@PathVariable("eventId") eventId) {
        termsCollection.findOne([
                domainId: eventId
        ])
    }

}

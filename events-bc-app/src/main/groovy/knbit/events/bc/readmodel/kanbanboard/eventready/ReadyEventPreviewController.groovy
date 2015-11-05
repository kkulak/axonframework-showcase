package knbit.events.bc.readmodel.kanbanboard.eventready

import com.mongodb.DBCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/events/")
class ReadyEventPreviewController {

    def DBCollection readyEventCollection

    @Autowired
    ReadyEventPreviewController(@Qualifier("readyevent") DBCollection readyEventCollection) {
        this.readyEventCollection = readyEventCollection
    }

    @RequestMapping(value = "/{readyEventId}/ready-event")
    def readyEventPreviewFor(@PathVariable readyEventId) {
        readyEventCollection.findOne([readyEventId: readyEventId])
    }

}

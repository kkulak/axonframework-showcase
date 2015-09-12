package knbit.events.bc.readmodel.kanbanboard.interest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by novy on 04.06.15.
 */
@RestController
@RequestMapping("/events")
class InterestPreviewController {

    def InterestPreviewRepository repository

    @Autowired
    InterestPreviewController(InterestPreviewRepository repository) {
        this.repository = repository
    }

    @RequestMapping(value = "/{eventId}/survey", method = RequestMethod.GET)
    def surveyWithQuestionnaire(@PathVariable("eventId") String eventId) {
        repository.load(eventId)
    }
}

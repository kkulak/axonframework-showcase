package knbit.events.bc.interest.viewmodel.eventmaster

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
class InterestSurveyingEventMasterViewModelController {

    def InterestSurveyingEventMasterViewModelRepository repository

    @Autowired
    InterestSurveyingEventMasterViewModelController(InterestSurveyingEventMasterViewModelRepository repository) {
        this.repository = repository
    }

    @RequestMapping(value = "/{eventId}/survey", method = RequestMethod.GET)
    def surveyWithQuestionnaire(@PathVariable("eventId") String eventId) {
        repository.load(eventId)
    }
}

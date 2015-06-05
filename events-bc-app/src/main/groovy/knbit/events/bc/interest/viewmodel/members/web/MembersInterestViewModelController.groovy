package knbit.events.bc.interest.viewmodel.members.web

import com.mongodb.DBCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = "/events")
class MembersInterestViewModelController {

    def surveyEventsCollection

    @Autowired
    MembersInterestViewModelController(
            @Qualifier("survey-events") DBCollection surveyCollection) {

        this.surveyEventsCollection = surveyCollection
    }

    @RequestMapping(value = "/survey", method = RequestMethod.GET)
    def surveyEvents() {
        surveyEventsCollection.find()
                .toArray()
    }

}

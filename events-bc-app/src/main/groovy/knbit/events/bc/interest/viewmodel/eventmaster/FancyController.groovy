package knbit.events.bc.interest.viewmodel.eventmaster

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by novy on 04.06.15.
 */
@RestController
class FancyController {

    def SurveyEventMasterViewModelRepository repository

    @Autowired
    FancyController(SurveyEventMasterViewModelRepository repository) {
        this.repository = repository
    }

    @RequestMapping("/groovy")
    String home() {
        return repository.somethingMore()
    }

}

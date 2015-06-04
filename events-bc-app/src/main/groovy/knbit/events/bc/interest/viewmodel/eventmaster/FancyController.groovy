package knbit.events.bc.interest.viewmodel.eventmaster

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by novy on 04.06.15.
 */
@RestController
class FancyController {

    @RequestMapping("/groovy")
    String home() {
        return "hello"
    }

}

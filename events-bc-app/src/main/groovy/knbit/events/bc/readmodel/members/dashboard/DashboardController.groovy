package knbit.events.bc.readmodel.members.dashboard

import knbit.events.bc.auth.Authenticated
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by novy on 21.10.15.
 */

@RestController
@RequestMapping(value = "/events")
class DashboardController {

    def DashboardQuery query

    @Autowired
    DashboardController(DashboardQuery query) {
        this.query = query
    }

    @Authenticated
    @RequestMapping(value = "/dashboard/{memberId}", method = RequestMethod.GET)
    def eventsWithPreferencesFor(@PathVariable("memberId") String memberId) {
        query.allWithPreferencesFor(memberId)
    }
}

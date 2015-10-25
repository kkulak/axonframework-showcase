package knbit.events.bc.readmodel.members.header

import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by novy on 25.10.15.
 */

@RestController
@RequestMapping(value = "/events")
class MembersHeaderController {

    def MembersHeaderQuery query

    @Autowired
    MembersHeaderController(MembersHeaderQuery query) {
        this.query = query
    }

    @RequestMapping(value = "/members/header", method = RequestMethod.GET)
    def headerFor(@PathVariable("memberId") String memberId) {
        query.newestHeaderData(MemberId.of(memberId))
    }
}

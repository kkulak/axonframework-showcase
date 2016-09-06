package knbit.events.bc.readmodel.members.enrollment

import knbit.events.bc.auth.Authenticated
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by novy on 11.10.15.
 */

@RestController
@RequestMapping(value = "/events")
class MemberEnrollmentViewModelController {

    def MemberEnrollmentQuery viewModelQuery

    @Autowired
    MemberEnrollmentViewModelController(MemberEnrollmentQuery viewModelQuery) {
        this.viewModelQuery = viewModelQuery
    }

    @Authenticated
    @RequestMapping(value = "/enrollment/{memberId}", method = RequestMethod.GET)
    def enrollmentEvents(@PathVariable("memberId") String memberId) {
        viewModelQuery.queryFor(
                MemberId.of(memberId)
        )
    }
}

package knbit.events.bc.readmodel.members.surveypreview

import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = "/events")
class MembersInterestViewModelController {

    def InterestMemberViewModelQuery viewModelQuery

    @Autowired
    MembersInterestViewModelController(InterestMemberViewModelQuery viewModelQuery) {
        this.viewModelQuery = viewModelQuery
    }

    @RequestMapping(value = "/survey/{memberId}", method = RequestMethod.GET)
    def surveyEvents(@PathVariable("memberId") String memberId) {
        viewModelQuery.queryFor(
                MemberId.of(memberId)
        )
    }

}

package knbit.events.bc.interest.viewmodel.eventmaster

import com.mongodb.DB
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * Created by novy on 04.06.15.
 */

@Repository
class SurveyEventMasterViewModelResource {

    def collection

    @Autowired
    SurveyEventMasterViewModelResource(DB database) {
        collection = database.surveyEventMasterViewModelCollection
    }

    def loadByEventDomainId(domainId) {
        return collection.findOne(domainId: domainId)
    }

    def add(interestAwareEvent) {
        collection.insert(interestAwareEvent)
    }

    def addQuestions(domainId, questions) {
        collection.update(
                [domainId: domainId],
                [$set: [questions: questions]]
        )
    }
}

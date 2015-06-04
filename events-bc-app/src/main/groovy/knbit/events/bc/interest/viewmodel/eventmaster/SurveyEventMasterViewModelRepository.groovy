package knbit.events.bc.interest.viewmodel.eventmaster

import com.mongodb.DB
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * Created by novy on 04.06.15.
 */

@Repository
class SurveyEventMasterViewModelRepository {

    def collection

    @Autowired
    SurveyEventMasterViewModelRepository(DB database) {
        collection = database.surveyEventMasterViewModelCollection
        doSomething()
    }

    def doSomething() {
        collection.insert(id: '3', msg: 'hello from mongo motherfucker')
    }

    def somethingMore() {
        return collection.findOne(id: '3').msg
    }
}

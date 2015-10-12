package knbit.events.bc.readmodel.kanbanboard.interest

import com.google.common.base.Preconditions
import com.mongodb.DBCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository

/**
 * Created by novy on 05.06.15.
 */

@Repository
class InterestPreviewRepository {

    def surveyCollection
    def questionnaireCollection

    @Autowired
    InterestPreviewRepository(
            @Qualifier("survey") DBCollection surveyCollection,
            @Qualifier("questionnaire") DBCollection questionnaireCollection) {

        this.surveyCollection = surveyCollection
        this.questionnaireCollection = questionnaireCollection
    }

    def load(eventId) {

        def withoutQuestions = surveyCollection.findOne(
                eventId: eventId
        )

        Preconditions.checkArgument(withoutQuestions != null, "No survey for %s", eventId as String)

        def questions = questionnaireCollection
                .find(eventId: eventId)
                .sort(questionNumber: 1)
                .toArray()

        if (questions) {
            withoutQuestions["questions"] = questions
        }

        withoutQuestions
    }
}

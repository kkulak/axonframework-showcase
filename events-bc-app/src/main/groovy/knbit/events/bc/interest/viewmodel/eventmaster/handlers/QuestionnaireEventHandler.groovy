package knbit.events.bc.interest.viewmodel.eventmaster.handlers

import com.mongodb.DBCollection
import knbit.events.bc.interest.domain.valueobjects.events.QuestionnaireAddedEvent
import knbit.events.bc.interest.domain.valueobjects.question.Question
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by novy on 04.06.15.
 */

@Component
class QuestionnaireEventHandler {

    def DBCollection collection

    @Autowired
    QuestionnaireEventHandler(DBCollection collection) {
        this.collection = collection
    }

    @EventHandler
    def on(QuestionnaireAddedEvent event) {
        def domainId = event.eventId().value()
        def questions = event.questions()

        addQuestions(domainId, prepareForAnswering(questions))
    }

    private def addQuestions(domainId, questions) {
        collection.update(
                [domainId: domainId],
                [$set: [questions: questions]]
        )
    }

    private static def prepareForAnswering(List<Question> questions) {
        questions.collect {
            def questionData = it.questionData()

            [
                    title       : questionData.title().value(),
                    description : questionData.description().value(),
                    questionType: questionData.answerType(),
                    answers     : questionData.possibleAnswers().collect {
                        [
                                value   : it.value(),
                                answered: 0
                        ]
                    }
            ]
        }
    }
}



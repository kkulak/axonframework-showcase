package knbit.events.bc.readmodel.members.surveypreview.handlers

import com.mongodb.DBCollection
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents
import knbit.events.bc.interest.domain.valueobjects.events.QuestionnaireEvents
import knbit.events.bc.interest.domain.valueobjects.question.Question
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class MemberSurveyEventHandler {

    def DBCollection collection

    @Autowired
    SurveyEventHandler(@Qualifier("survey-events") DBCollection collection) {
        this.collection = collection
    }

    @EventHandler
    def on(InterestAwareEvents.Created event) {
        def eventId = event.eventId()
        def eventDetails = event.eventDetails()

        collection.insert([
                eventId       : eventId.value(),
                name          : eventDetails.name().value(),
                description   : eventDetails.description().value(),
                eventType     : eventDetails.type(),
                eventFrequency: eventDetails.frequency()
        ])
    }

    @EventHandler
    def on(QuestionnaireEvents.Added event) {
        def domainId = event.eventId().value()
        def questions = event.questions()

        def questionsArray = flatArray(questions)

        collection.update(
                [domainId: domainId],
                [$set: [questions: questionsArray]]
        )

    }

    private static def flatArray(List<Question> questions) {
        def questionsArray = []

        questions.each {
            def data = it.questionData()
            def answers = []

            data.possibleAnswers().each {
                answers.push(it.value())
            }

            questionsArray.push([
                    title      : data.title().value(),
                    description: data.description().value(),
                    type       : data.answerType(),
                    answers    : answers
            ])
        }

        questionsArray
    }

}

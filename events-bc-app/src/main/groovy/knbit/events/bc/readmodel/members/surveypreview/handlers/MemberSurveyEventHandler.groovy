package knbit.events.bc.readmodel.members.surveypreview.handlers

import com.mongodb.DBCollection
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents
import knbit.events.bc.interest.domain.valueobjects.events.QuestionnaireEvents
import knbit.events.bc.interest.domain.valueobjects.question.Question
import knbit.events.bc.readmodel.RemoveEventRelatedData
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class MemberSurveyEventHandler implements RemoveEventRelatedData {

    def DBCollection collection

    @Autowired
    MemberSurveyEventHandler(@Qualifier("survey-events") DBCollection collection) {
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
                eventFrequency: eventDetails.frequency(),
                votedUp       : 0
        ])
    }

    @EventHandler
    def on(QuestionnaireEvents.Added event) {
        def domainId = event.eventId().value()
        def questions = event.questions()

        def questionsArray = flatArray(questions)

        collection.update(
                [eventId: domainId],
                [$set: [questions: questionsArray]]
        )
    }

    @EventHandler
    def on(InterestAwareEvents.TransitedToUnderChoosingTerm event) {
        removeDataBy(event.eventId()).from(collection)
    }

    private static def flatArray(List<Question> questions) {

        questions.collect {
            def questionData = it.questionData()

            def answers = questionData
                    .possibleAnswers()
                    .collect { it.value() }

            [
                    title      : questionData.title().value(),
                    description: questionData.description().value(),
                    type       : questionData.answerType(),
                    answers    : answers
            ]
        }
    }
}

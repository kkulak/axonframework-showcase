package knbit.events.bc.interest.viewmodel.eventmaster

import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEventCreated
import knbit.events.bc.interest.domain.valueobjects.events.QuestionnaireAddedEvent
import knbit.events.bc.interest.domain.valueobjects.question.Question
import org.axonframework.eventhandling.annotation.EventHandler

/**
 * Created by novy on 04.06.15.
 */
class SurveyEventMasterViewModelCommandHandler {

    def SurveyEventMasterViewModelResource repository

    SurveyEventMasterViewModelCommandHandler(SurveyEventMasterViewModelResource repository) {
        this.repository = repository
    }

    @EventHandler
    def on(InterestAwareEventCreated event) {
        def eventId = event.eventId()
        def eventDetails = event.eventDetails()

        repository.add([
                domainId      : eventId.value(),
                name          : eventDetails.name().value(),
                description   : eventDetails.description().value(),
                eventType     : eventDetails.type(),
                eventFrequency: eventDetails.frequency()
        ])
    }

    @EventHandler
    def on(QuestionnaireAddedEvent event) {
        def domainId = event.eventId().value()
        def questions = event.questions()

        repository.addQuestions(
                domainId, prepareForAnswering(questions)
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

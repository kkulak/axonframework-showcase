package knbit.events.bc.readmodel.kanbanboard.interest.handlers

import com.mongodb.DBCollection
import knbit.events.bc.interest.domain.enums.AnswerType

import knbit.events.bc.interest.domain.valueobjects.events.QuestionnaireEvents
import knbit.events.bc.interest.domain.valueobjects.question.Question
import knbit.events.bc.interest.domain.valueobjects.question.answer.AnsweredQuestion
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 04.06.15.
 */

@Component
class QuestionnaireEventHandler {

    def DBCollection collection

    @Autowired
    QuestionnaireEventHandler(@Qualifier("questionnaire") DBCollection collection) {
        this.collection = collection
    }

    @EventHandler
    def on(QuestionnaireEvents.Added event) {
        def domainId = event.eventId().value()
        def questions = event.questions()

        collection << prepareForAnswering(domainId, questions)
    }

    @EventHandler
    def on(QuestionnaireEvents.CompletedByAttendee event) {
        def eventId = event.eventId()
        completeQuestionnaire(eventId.value(), event.answeredQuestions())
    }

    private def completeQuestionnaire(String domainId, Collection<AnsweredQuestion> answeredQuestions) {
        def textQuestions = answeredQuestions.findAll {
            it.questionData().answerType() == AnswerType.TEXT
        }

        def singleAndMultipleChoice = answeredQuestions - textQuestions

        answerTextQuestions(domainId, textQuestions)
        answerSingleAndMultipleChoiceQuestions(domainId, singleAndMultipleChoice)

    }

    private def answerTextQuestions(String domainId, Collection<AnsweredQuestion> questions) {
        // todo: $each not working ??
        questions.each {
            def questionData = it.questionData()

            it.answers().each {
                collection.update(
                        [
                                domainId    : domainId,
                                title       : questionData.title().value(),
                                description : questionData.description().value(),
                                questionType: questionData.answerType()
                        ],
                        [
                                $push: [
                                        answers: it.value()
                                ]
                        ]
                )
            }
        }
    }

    def answerSingleAndMultipleChoiceQuestions(String domainId, Collection<AnsweredQuestion> answeredQuestions) {

        answeredQuestions.each {
            def questionData = it.questionData()
            it.answers().each {
                collection.update(
                        [
                                domainId       : domainId,
                                title          : questionData.title().value(),
                                description    : questionData.description().value(),
                                questionType   : questionData.answerType(),
                                "answers.value": it.value()
                        ],
                        [$inc: ['answers.$.answered': 1]]
                )
            }
        }
    }

    private static def prepareForAnswering(String domainId, List<Question> questions) {
        def questionNumber = 0;

        questions.collect {
            def questionData = it.questionData()

            [
                    domainId      : domainId,
                    questionNumber: questionNumber++,
                    title         : questionData.title().value(),
                    description   : questionData.description().value(),
                    questionType  : questionData.answerType(),
                    answers       : questionData.possibleAnswers().collect {
                        [
                                value   : it.value(),
                                answered: 0
                        ]
                    }
            ]
        }
    }
}



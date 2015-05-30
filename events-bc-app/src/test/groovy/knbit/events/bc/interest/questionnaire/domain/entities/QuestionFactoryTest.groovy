package knbit.events.bc.interest.questionnaire.domain.entities

import knbit.events.bc.interest.questionnaire.domain.enums.QuestionType
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionId
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.IdentifiedQuestionData
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionData
import spock.lang.Specification

/**
 * Created by novy on 26.05.15.
 */
class QuestionFactoryTest extends Specification {

    def "should return SingleChoiceQuestion given SINGLE_CHOICE question type"() {

        given:
        def identifiedQuestionData = IdentifiedQuestionData.of(
                QuestionId.of("id"),
                QuestionData.of("q", "d", QuestionType.SINGLE_CHOICE, ["choice1", "choice2"])
        )

        expect:
        QuestionFactory.newQuestion(identifiedQuestionData) instanceof SingleChoiceQuestion

    }

    def "should return MultipleChoiceQuestion given MULTIPLE_CHOICE question type"() {

        given:
        def identifiedQuestionData = IdentifiedQuestionData.of(
                QuestionId.of("id"),
                QuestionData.of("q", "d", QuestionType.MULTIPLE_CHOICE, ["choice1", "choice2"])
        )

        expect:
        QuestionFactory.newQuestion(identifiedQuestionData) instanceof MultipleChoiceQuestion

    }

    def "should return TextQuestion given TEXT question type"() {

        given:
        def identifiedQuestionData = IdentifiedQuestionData.of(
                QuestionId.of("id"),
                QuestionData.of("q", "d", QuestionType.TEXT, [])
        )

        expect:
        QuestionFactory.newQuestion(identifiedQuestionData) instanceof TextQuestion

    }
}

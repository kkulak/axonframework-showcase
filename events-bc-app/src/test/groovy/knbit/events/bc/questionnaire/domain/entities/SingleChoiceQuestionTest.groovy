package knbit.events.bc.questionnaire.domain.entities

import knbit.events.bc.questionnaire.domain.enums.QuestionType
import knbit.events.bc.questionnaire.domain.exceptions.IncorrectAnswerTypeException
import knbit.events.bc.questionnaire.domain.exceptions.IncorrectChoiceException
import knbit.events.bc.questionnaire.domain.exceptions.QuestionIdDoesNotMatchException
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionId
import knbit.events.bc.questionnaire.domain.valueobjects.question.AnsweredQuestion
import knbit.events.bc.questionnaire.domain.valueobjects.question.DomainAnswer
import knbit.events.bc.questionnaire.domain.valueobjects.question.QuestionDescription
import knbit.events.bc.questionnaire.domain.valueobjects.question.QuestionTitle
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.MultipleChoiceAnswer
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.SingleChoiceAnswer
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.TextAnswer
import spock.lang.Specification

/**
 * Created by novy on 26.05.15.
 */
class SingleChoiceQuestionTest extends Specification {

    def QuestionId questionId
    def QuestionTitle title
    def QuestionDescription description
    def List<DomainAnswer> allowedAnswers

    void setup() {
        questionId = QuestionId.of("anId")
        title = QuestionTitle.of("title")
        description = QuestionDescription.of("desc")

        allowedAnswers = [DomainAnswer.of("ans1"), DomainAnswer.of("ans2"), DomainAnswer.of("ans3")]

    }

    def "should throw an exception when ids don't match"() {

        given:
        def objectUnderTest = new SingleChoiceQuestion(
                questionId, title, description, allowedAnswers
        )

        when:
        def answer = new SingleChoiceAnswer(
                QuestionId.of("fakeId"), "ans1"
        )
        objectUnderTest.check(answer)


        then:
        thrown(QuestionIdDoesNotMatchException.class)

    }

    def "should throw an exception when given incorrect answer"() {

        given:
        def objectUnderTest = new SingleChoiceQuestion(
                questionId, title, description, allowedAnswers
        )

        when:
        def answer = new SingleChoiceAnswer(
                questionId, "dummy answer"
        )
        objectUnderTest.check(answer)

        then:
        thrown(IncorrectChoiceException.class)

    }

    def "should throw an exception given MultipleChoiceAnswer"() {

        given:
        def objectUnderTest = new SingleChoiceQuestion(
                questionId, title, description, allowedAnswers
        )

        when:
        def answer = new MultipleChoiceAnswer(
                QuestionId.of("anId"), ["ans1"]
        )
        objectUnderTest.check(answer)

        then:
        thrown(IncorrectAnswerTypeException.class)
    }

    def "should throw an exception given TextAnswer"() {

        given:
        def objectUnderTest = new SingleChoiceQuestion(
                questionId, title, description, allowedAnswers
        )

        when:
        def answer = new TextAnswer(
                QuestionId.of("anId"), "ans1"
        )
        objectUnderTest.check(answer)

        then:
        thrown(IncorrectAnswerTypeException.class)
    }

    def "should return AnsweredQuestion which glues question and answer otherwise"() {

        given:
        def objectUnderTest = new SingleChoiceQuestion(
                questionId, title, description, allowedAnswers
        )

        when:
        def answer = new SingleChoiceAnswer(
                questionId, "ans3"
        )
        def answeredQuestion = objectUnderTest.check(answer)

        then:
        answeredQuestion == AnsweredQuestion.of(
                questionId, title, description, QuestionType.SINGLE_CHOICE,
                allowedAnswers, [DomainAnswer.of("ans3")]
        )
    }
}

package knbit.events.bc.interest.questionnaire.domain.entities

import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionDescription
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionTitle
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.answer.DomainAnswer
import spock.lang.Specification

/**
 * Created by novy on 26.05.15.
 */
class MultipleChoiceQuestionTest extends Specification {

    def QuestionTitle title
    def QuestionDescription description
    def List<DomainAnswer> allowedAnswers

    void setup() {
        title = QuestionTitle.of("title")
        description = QuestionDescription.of("desc")

        allowedAnswers = [DomainAnswer.of("ans1"), DomainAnswer.of("ans2"), DomainAnswer.of("ans3")]

    }

/*    def "should throw an exception when given incorrect answer"() {

        given:
        def objectUnderTest = new MultipleChoiceQuestion(
                questionId, title, description, allowedAnswers
        )

        when:
        def answer = new MultipleChoiceAnswer(
                questionId, ["ans1", "wrong answer"]
        )
        objectUnderTest.check(answer)


        then:
        thrown(IncorrectChoiceException.class)

    }

    def "should throw an exception given SingleChoiceAnswer"() {

        given:
        def objectUnderTest = new MultipleChoiceQuestion(
                questionId, title, description, allowedAnswers
        )

        when:
        def answer = new SingleChoiceAnswer(
                QuestionId.of("anId"), "ans1"
        )
        objectUnderTest.check(answer)

        then:
        thrown(IncorrectAnswerTypeException.class)
    }

    def "should throw an exception given TextAnswer"() {

        given:
        def objectUnderTest = new MultipleChoiceQuestion(
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
        def objectUnderTest = new MultipleChoiceQuestion(
                questionId, title, description, allowedAnswers
        )

        when:
        def answer = new MultipleChoiceAnswer(
                questionId, ["ans2", "ans3"]
        )
        def answeredQuestion = objectUnderTest.check(answer)

        then:
        answeredQuestion == AnsweredQuestion.of(
                questionId, title, description, QuestionType.MULTIPLE_CHOICE,
                allowedAnswers, [DomainAnswer.of("ans2"), DomainAnswer.of("ans3")]
        )
    }*/
}

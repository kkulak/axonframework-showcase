package knbit.events.bc.interest.questionnaire.domain.entities

import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionDescription
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionTitle
import spock.lang.Specification

/**
 * Created by novy on 26.05.15.
 */
class TextQuestionTest extends Specification {

    def QuestionTitle title
    def QuestionDescription description

    void setup() {
        title = QuestionTitle.of("title")
        description = QuestionDescription.of("desc")
    }

/*    def "should throw an exception given MultipleChoiceAnswer"() {

        given:
        def objectUnderTest = new TextQuestion(
                questionId, title, description
        )

        when:
        def answer = new MultipleChoiceAnswer(
                QuestionId.of("anId"), ["ans1"]
        )
        objectUnderTest.check(answer)

        then:
        thrown(IncorrectAnswerTypeException.class)
    }

    def "should throw an exception given SingleChoiceAnswer"() {

        given:
        def objectUnderTest = new TextQuestion(
                questionId, title, description
        )

        when:
        def answer = new SingleChoiceAnswer(
                QuestionId.of("anId"), "ans1"
        )
        objectUnderTest.check(answer)

        then:
        thrown(IncorrectAnswerTypeException.class)
    }

    def "should return AnsweredQuestion which glues question and answer otherwise"() {

        given:
        def objectUnderTest = new TextQuestion(
                questionId, title, description
        )

        when:
        def answer = new TextAnswer(
                questionId, "random answer"
        )
        def answeredQuestion = objectUnderTest.check(answer)

        then:
        answeredQuestion == AnsweredQuestion.of(
                questionId, title, description, QuestionType.TEXT, [], [DomainAnswer.of("random answer")]
        )
    }*/
}

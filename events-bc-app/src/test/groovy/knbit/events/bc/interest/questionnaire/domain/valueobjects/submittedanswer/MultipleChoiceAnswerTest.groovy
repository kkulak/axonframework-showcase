package knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer

import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.DomainAnswer
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionId
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.MultipleChoiceAnswer
import spock.lang.Specification

/**
 * Created by novy on 26.05.15.
 */
class MultipleChoiceAnswerTest extends Specification {

    def "should not allow to pass empty answer list"() {

        when:
        new MultipleChoiceAnswer(
                QuestionId.of("id"), []
        )

        then:
        thrown(IllegalArgumentException.class)

    }

    def "should not allow to pass an empty string as answer"() {

        when:
        new MultipleChoiceAnswer(
                QuestionId.of("id"), ["ONE", "", "THREE"]
        )

        then:
        thrown(IllegalArgumentException.class)

    }

    def "should not allow to pass null answer"() {

        when:
        new MultipleChoiceAnswer(
                QuestionId.of("id"), ["ONE", null, "THREE"]
        )

        then:
        thrown(IllegalArgumentException.class)

    }

    def "should unwrap to list of DomainAnswers"() {

        given:
        def objectUnderTest = new MultipleChoiceAnswer(
                QuestionId.of("id"), ["ONE", "TWO", "THREE"]
        )

        expect:
        objectUnderTest.unwrap() == [
                DomainAnswer.of("ONE"),
                DomainAnswer.of("TWO"),
                DomainAnswer.of("THREE")
        ]

    }
}

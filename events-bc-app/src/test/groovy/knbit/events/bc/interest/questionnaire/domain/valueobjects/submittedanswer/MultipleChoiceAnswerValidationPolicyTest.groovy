package knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer

import spock.lang.Specification

/**
 * Created by novy on 26.05.15.
 */
class MultipleChoiceAnswerValidationPolicyTest extends Specification {

/*    def "should not allow to pass empty answer list"() {

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
        objectUnderTest.answers() == [
                DomainAnswer.of("ONE"),
                DomainAnswer.of("TWO"),
                DomainAnswer.of("THREE")
        ]

    }*/
}

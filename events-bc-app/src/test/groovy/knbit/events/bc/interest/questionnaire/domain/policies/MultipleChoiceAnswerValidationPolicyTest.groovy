package knbit.events.bc.interest.questionnaire.domain.policies

import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.answer.DomainAnswer
import spock.lang.Specification

class MultipleChoiceAnswerValidationPolicyTest extends Specification {
    def List<DomainAnswer> allowedAnswers

    void setup() {
        allowedAnswers = [DomainAnswer.of("ans1"), DomainAnswer.of("ans2"), DomainAnswer.of("ans3")]
    }

    def "should return false given incorrect answer"() {
        given:
        def validationPolicy = new MultipleChoiceAnswerValidationPolicy()

        when:
        def answer = [DomainAnswer.of("incorrect answer"), DomainAnswer.of("ans2")]
        def isValid = validationPolicy.validate(allowedAnswers, answer)

        then:
        !isValid
    }

    def "should return false given empty answer list"() {
        given:
        def validationPolicy = new MultipleChoiceAnswerValidationPolicy()

        when:
        def answer = []
        def isValid = validationPolicy.validate(allowedAnswers, answer)

        then:
        !isValid
    }

    def "should return true given valid answers"() {
        given:
        def validationPolicy = new MultipleChoiceAnswerValidationPolicy()

        when:
        def answer = [DomainAnswer.of("ans3"), DomainAnswer.of("ans1")]
        def isValid = validationPolicy.validate(allowedAnswers, answer)

        then:
        isValid
    }

}

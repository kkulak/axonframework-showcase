package knbit.events.bc.interest.questionnaire.domain.policies

import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.answer.DomainAnswer
import spock.lang.Specification

class TextChoiceAnswerValidationPolicyTest extends Specification {
    def List<DomainAnswer> allowedAnswers

    void setup() {
        allowedAnswers = []
    }

    def "should return false given non empty answer list"() {
        given:
        def validationPolicy = new TextChoiceAnswerValidationPolicy()

        when:
        def answer = [DomainAnswer.of("incorrect answer")]
        def isValid = validationPolicy.validate(allowedAnswers, answer)

        then:
        !isValid
    }

    def "should return true given valid answer"() {
        given:
        def validationPolicy = new TextChoiceAnswerValidationPolicy()

        when:
        def answer = []
        def isValid = validationPolicy.validate(allowedAnswers, answer)

        then:
        isValid
    }

}

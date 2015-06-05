package knbit.events.bc.interest.questionnaire.domain.policies

import knbit.events.bc.interest.domain.policies.completingquestionnaire.TextChoiceAnswerPolicy
import knbit.events.bc.interest.domain.valueobjects.question.answer.DomainAnswer
import spock.lang.Specification

class TextChoiceAnswerPolicyTest extends Specification {

    def "should return false given non empty answer list"() {
        given:
        def validationPolicy = new TextChoiceAnswerPolicy()

        when:
        def answer = [DomainAnswer.of("incorrect answer")]
        def isValid = validationPolicy.validate(answer)

        then:
        !isValid
    }

    def "should return true given empty answer list"() {
        given:
        def validationPolicy = new TextChoiceAnswerPolicy()

        when:
        def answer = []
        def isValid = validationPolicy.validate(answer)

        then:
        isValid
    }

}

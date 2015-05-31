package knbit.events.bc.interest.questionnaire.domain.policies

import knbit.events.bc.interest.domain.policies.completingquestionnaire.TextChoiceAnswerPolicy
import knbit.events.bc.interest.domain.valueobjects.question.answer.DomainAnswer
import spock.lang.Specification

class TextChoiceAnswerPolicyTest extends Specification {
    def List<DomainAnswer> allowedAnswers

    void setup() {
        allowedAnswers = []
    }

    def "should throw NullPointerException given null object of possible answers"() {
        when:
        new TextChoiceAnswerPolicy(null)

        then:
        thrown(NullPointerException)
    }

    def "should thrown IllegalArgumentException given non empty list of possible answers"() {
        when:
        new TextChoiceAnswerPolicy([DomainAnswer.of("ans")])

        then:
        thrown(IllegalArgumentException)
    }

    def "should return false given non empty answer list"() {
        given:
        def validationPolicy = new TextChoiceAnswerPolicy(allowedAnswers)

        when:
        def answer = [DomainAnswer.of("incorrect answer")]
        def isValid = validationPolicy.validate(answer)

        then:
        !isValid
    }

    def "should return true given valid answer"() {
        given:
        def validationPolicy = new TextChoiceAnswerPolicy(allowedAnswers)

        when:
        def answer = []
        def isValid = validationPolicy.validate(answer)

        then:
        isValid
    }

}

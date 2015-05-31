package knbit.events.bc.interest.questionnaire.domain.policies

import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.answer.DomainAnswer
import spock.lang.Specification

class SingleChoiceAnswerPolicyTest extends Specification {
    def List<DomainAnswer> allowedAnswers

    void setup() {
        allowedAnswers = [DomainAnswer.of("ans1"), DomainAnswer.of("ans2"), DomainAnswer.of("ans3")]
    }

    def "should throw NullPointerException given null object of possible answers"() {
        when:
        new SingleChoiceAnswerPolicy(null)

        then:
        thrown(NullPointerException)
    }

    def "should thrown IllegalArgumentException given empty list of possible answers"() {
        when:
        new SingleChoiceAnswerPolicy([])

        then:
        thrown(IllegalArgumentException)
    }

    def "should return false given incorrect answer"() {
        given:
        def validationPolicy = new SingleChoiceAnswerPolicy(allowedAnswers)

        when:
        def answer = [DomainAnswer.of("incorrect answer")]
        def isValid = validationPolicy.validate(answer)

        then:
        !isValid
    }

    def "should return false given multiple answers"() {
        given:
        def validationPolicy = new SingleChoiceAnswerPolicy(allowedAnswers)

        when:
        def answer = [DomainAnswer.of("ans1"), DomainAnswer.of("ans2")]
        def isValid = validationPolicy.validate(answer)

        then:
        !isValid
    }

    def "should return false given empty list of answers"() {
        given:
        def validationPolicy = new SingleChoiceAnswerPolicy(allowedAnswers)

        when:
        def answer = []
        def isValid = validationPolicy.validate(answer)

        then:
        !isValid
    }

    def "should return true given valid answer"() {
        given:
        def validationPolicy = new SingleChoiceAnswerPolicy(allowedAnswers)

        when:
        def answer = [DomainAnswer.of("ans3")]
        def isValid = validationPolicy.validate(answer)

        then:
        isValid
    }

}

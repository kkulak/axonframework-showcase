package knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer

import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionId
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.DomainAnswer
import spock.lang.Specification

/**
 * Created by novy on 26.05.15.
 */
class SingleChoiceAnswerTest extends Specification {

    def "should unwrap to single element list of Domain Answers"() {

        given:
        def objectUnderTest = new SingleChoiceAnswer(
                QuestionId.of("id"), "ANSWER"
        )

        expect:
        objectUnderTest.unwrap() == [DomainAnswer.of("ANSWER")]

    }
}

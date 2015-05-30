package knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer

import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionId
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.DomainAnswer
import spock.lang.Specification

/**
 * Created by novy on 26.05.15.
 */
class TextAnswerTest extends Specification {

    def "should unwrap to single element list of Domain Answers"() {

        given:
        def objectUnderTest = new TextAnswer(
                QuestionId.of("id"), "this is a fancy answer!"
        )

        expect:
        objectUnderTest.unwrap() == [DomainAnswer.of("this is a fancy answer!")]

    }
}

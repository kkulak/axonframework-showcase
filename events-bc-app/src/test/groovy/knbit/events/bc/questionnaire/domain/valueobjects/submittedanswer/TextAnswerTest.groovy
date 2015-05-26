package knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer

import knbit.events.bc.questionnaire.domain.valueobjects.DomainAnswer
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionnaireId
import spock.lang.Specification

/**
 * Created by novy on 26.05.15.
 */
class TextAnswerTest extends Specification {

    def "should unwrap to single element list of Domain Answers"() {

        given:
        def objectUnderTest = new TextAnswer(
                QuestionnaireId.of("id"), "this is a fancy answer!"
        )

        expect:
        objectUnderTest.unwrap() == [DomainAnswer.of("this is a fancy answer!")]

    }
}

package knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer

import knbit.events.bc.questionnaire.domain.valueobjects.question.DomainAnswer
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionnaireId
import spock.lang.Specification

/**
 * Created by novy on 26.05.15.
 */
class SingleChoiceAnswerTest extends Specification {

    def "should unwrap to single element list of Domain Answers"() {

        given:
        def objectUnderTest = new SingleChoiceAnswer(
                QuestionnaireId.of("id"), "ANSWER"
        )

        expect:
        objectUnderTest.unwrap() == [DomainAnswer.of("ANSWER")]

    }
}

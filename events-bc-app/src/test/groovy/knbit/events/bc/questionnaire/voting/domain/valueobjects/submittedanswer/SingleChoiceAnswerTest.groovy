package knbit.events.bc.questionnaire.voting.domain.valueobjects.submittedanswer

import knbit.events.bc.questionnaire.voting.domain.valueobjects.DomainAnswer
import knbit.events.bc.questionnaire.voting.domain.valueobjects.QuestionnaireId
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

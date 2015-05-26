package knbit.events.bc.questionnaire.domain.valueobjects

import knbit.events.bc.questionnaire.domain.enums.QuestionType
import knbit.events.bc.questionnaire.domain.valueobjects.question.QuestionData
import spock.lang.Specification

/**
 * Created by novy on 26.05.15.
 */
class QuestionDataTest extends Specification {

    def "should not be able to pass any answer given Text question type"() {

        when:
        new QuestionData(
                "title", "desc", QuestionType.TEXT, ["forbiddenPossibleAnswer"]
        )

        then:
        thrown(IllegalArgumentException.class)

    }

    def "should not be able to pass empty answer list given MultipleChoice question"() {

        when:
        new QuestionData(
                "title", "desc", QuestionType.MULTIPLE_CHOICE, []
        )

        then:
        thrown(IllegalArgumentException.class)

    }

    def "should not be able to pass empty answer list given SingleChoice question"() {

        when:
        new QuestionData(
                "title", "desc", QuestionType.SINGLE_CHOICE, []
        )

        then:
        thrown(IllegalArgumentException.class)

    }
}

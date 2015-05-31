package knbit.events.bc.interest.questionnaire.domain.valueobjects.question

import knbit.events.bc.interest.domain.enums.AnswerType
import knbit.events.bc.interest.domain.exceptions.IncorrectChoiceException
import knbit.events.bc.interest.domain.policies.completingquestionnaire.AnswerPolicy
import knbit.events.bc.interest.domain.valueobjects.question.Question
import knbit.events.bc.interest.domain.valueobjects.question.QuestionData
import knbit.events.bc.interest.domain.valueobjects.question.QuestionDescription
import knbit.events.bc.interest.domain.valueobjects.question.QuestionTitle
import knbit.events.bc.interest.domain.valueobjects.question.answer.AnsweredQuestion
import knbit.events.bc.interest.domain.valueobjects.question.answer.DomainAnswer
import knbit.events.bc.interest.domain.valueobjects.submittedanswer.SubmittedAnswer
import spock.lang.Specification

class QuestionTest extends Specification {
    def AnswerPolicy answerPolicy
    def List<DomainAnswer> possibleAnswers
    def QuestionData questionData

    void setup() {
        answerPolicy = Mock(AnswerPolicy)
        possibleAnswers = [DomainAnswer.of("ans1")]
        questionData = QuestionData.of(
                QuestionTitle.of("title"), QuestionDescription.of("description"), AnswerType.SINGLE_CHOICE, [DomainAnswer.of("ans1")]
        )
    }

    def "should thrown exception given answer not meeting answer policy"() {
        given:
        answerPolicy.validate(_ as List<DomainAnswer>) >> { return false }
        def submittedAnswer = SubmittedAnswer.of(questionData, [DomainAnswer.of("ans1")])
        def question = Question.of(QuestionTitle.of("title"), QuestionDescription.of("description"), answerPolicy)

        when:
        question.answer(submittedAnswer)

        then:
        thrown(IncorrectChoiceException)
    }

    def "should return AnsweredQuestion fulfilled object on answer meeting answer policy"() {
        given:
        answerPolicy.validate(_ as List<DomainAnswer>) >> { return true }
        answerPolicy.answerType() >> { return AnswerType.SINGLE_CHOICE }
        answerPolicy.answers() >> { return [DomainAnswer.of("ans1")] }
        def submittedAnswer = SubmittedAnswer.of(questionData, [DomainAnswer.of("ans1")])
        def question = Question.of(QuestionTitle.of("title"), QuestionDescription.of("description"), answerPolicy)

        when:
        def answeredQuestion = question.answer(submittedAnswer)

        then:
        answeredQuestion == AnsweredQuestion.of(questionData, [DomainAnswer.of("ans1")])
    }

}

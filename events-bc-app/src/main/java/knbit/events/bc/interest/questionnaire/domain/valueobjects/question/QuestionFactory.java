package knbit.events.bc.interest.questionnaire.domain.valueobjects.question;

import knbit.events.bc.interest.questionnaire.domain.enums.QuestionType;
import knbit.events.bc.interest.questionnaire.domain.policies.AnswerValidationPolicy;
import knbit.events.bc.interest.questionnaire.domain.policies.MultipleChoiceAnswerValidationPolicy;
import knbit.events.bc.interest.questionnaire.domain.policies.SingleChoiceAnswerValidationPolicy;
import knbit.events.bc.interest.questionnaire.domain.policies.TextChoiceAnswerValidationPolicy;

/**
 * Created by novy on 26.05.15.
 */
public class QuestionFactory {

    public static Question newQuestion(QuestionData questionData) {
        final QuestionType questionType = questionData.questionType();
        final AnswerValidationPolicy validationPolicy = findPolicy(questionType);

        return new Question(
                questionData.title(),
                questionData.description(),
                validationPolicy,
                questionData.possibleAnswers()
        );
    }

    private static AnswerValidationPolicy findPolicy(QuestionType type) {
        switch (type) {
            case SINGLE_CHOICE:
                return new SingleChoiceAnswerValidationPolicy();
            case MULTIPLE_CHOICE:
                return new MultipleChoiceAnswerValidationPolicy();
            case TEXT:
                return new TextChoiceAnswerValidationPolicy();
            default:
                throw new IllegalArgumentException();
        }
    }

}

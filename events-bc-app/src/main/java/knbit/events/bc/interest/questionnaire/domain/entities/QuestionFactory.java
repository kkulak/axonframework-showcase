package knbit.events.bc.interest.questionnaire.domain.entities;

import knbit.events.bc.interest.questionnaire.domain.enums.QuestionType;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.IdentifiedQuestionData;

/**
 * Created by novy on 26.05.15.
 */
public class QuestionFactory {

    public static Question newQuestion(IdentifiedQuestionData questionData) {
        final QuestionType questionType = questionData.questionType();

        switch (questionType) {
            case SINGLE_CHOICE:
                return new SingleChoiceQuestion(
                        questionData.questionId(), questionData.title(),
                        questionData.description(), questionData.possibleAnswers()
                );
            case MULTIPLE_CHOICE:
                return new MultipleChoiceQuestion(
                        questionData.questionId(), questionData.title(),
                        questionData.description(), questionData.possibleAnswers()
                );
            case TEXT:
                return new TextQuestion(
                        questionData.questionId(), questionData.title(), questionData.description()
                );
            default:
                throw new IllegalArgumentException();
        }
    }
}

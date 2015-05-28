package knbit.events.bc.interest.questionnaire.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.CheckableAnswer;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionId;

/**
 * Created by novy on 26.05.15.
 */
public class IncorrectAnswerTypeException extends DomainException {

    private static final String ERROR_MESSAGE_TEMPLATE =
            "Incorrect answer type for Question with ID %s. Expected: %s, got: %s";

    public IncorrectAnswerTypeException(QuestionId questionId,
                                        Class<? extends CheckableAnswer> expectedAnswerClass,
                                        Class<? extends CheckableAnswer> actualAnswerClass) {

        super(String.format(ERROR_MESSAGE_TEMPLATE, questionId, expectedAnswerClass, actualAnswerClass));
    }
}

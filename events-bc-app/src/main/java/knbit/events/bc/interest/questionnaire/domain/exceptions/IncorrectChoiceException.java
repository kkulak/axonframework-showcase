package knbit.events.bc.interest.questionnaire.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionId;

/**
 * Created by novy on 26.05.15.
 */
public class IncorrectChoiceException extends DomainException {

    private static final String ERROR_MESSAGE_TEMPLATE =
            "Incorrect answer option for question with id %s";

    public IncorrectChoiceException(QuestionId questionId) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, questionId));
    }
}

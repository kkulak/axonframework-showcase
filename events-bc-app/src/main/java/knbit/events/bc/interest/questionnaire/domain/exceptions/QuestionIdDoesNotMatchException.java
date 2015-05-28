package knbit.events.bc.interest.questionnaire.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionId;

/**
 * Created by novy on 26.05.15.
 */
public class QuestionIdDoesNotMatchException extends DomainException {

    private static final String ERROR_MESSAGE_TEMPLATE =
            "Question and answer ID don't match. Expected: %s, actual: %s";

    public QuestionIdDoesNotMatchException(QuestionId expectedId, QuestionId actualId) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, expectedId, actualId));
    }
}

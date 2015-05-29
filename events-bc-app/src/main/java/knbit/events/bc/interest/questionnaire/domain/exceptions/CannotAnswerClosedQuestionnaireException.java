package knbit.events.bc.interest.questionnaire.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import knbit.events.bc.interest.survey.domain.valueobjects.SurveyId;

/**
 * Created by novy on 28.05.15.
 */
public class CannotAnswerClosedQuestionnaireException extends DomainException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Questionnaire already closed (ID = %s)";

    public CannotAnswerClosedQuestionnaireException(QuestionnaireId questionnaireId) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, questionnaireId));
    }
}

package knbit.events.bc.interest.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.interest.domain.valueobjects.SurveyId;

/**
 * Created by novy on 28.05.15.
 */
public class CannotVoteOnClosedSurveyException extends DomainException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Cannot vote on closed survey! (ID=%s)";

    public CannotVoteOnClosedSurveyException(SurveyId surveyId) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, surveyId));
    }
}

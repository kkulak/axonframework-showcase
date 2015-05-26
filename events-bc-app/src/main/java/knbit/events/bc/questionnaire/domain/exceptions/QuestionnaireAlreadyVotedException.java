package knbit.events.bc.questionnaire.domain.exceptions;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionnaireId;

/**
 * Created by novy on 26.05.15.
 */
public class QuestionnaireAlreadyVotedException extends DomainException {

    private static final String ERROR_MESSAGE_TEMPLATE =
            "Questionnaire with id %s already voted by attendee %s";

    public QuestionnaireAlreadyVotedException(QuestionnaireId questionnaireId, Attendee attendee) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, questionnaireId, attendee));
    }
}

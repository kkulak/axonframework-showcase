package knbit.events.bc.questionnaire.domain.aggregates;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionnaireId;

/**
 * Created by novy on 26.05.15.
 */
public class QuestionnaireFactory {

    public static Questionnaire newQuestionnaire(QuestionnaireId questionnaireId, EventId eventId) {
        return new Questionnaire(questionnaireId, eventId);
    }

}

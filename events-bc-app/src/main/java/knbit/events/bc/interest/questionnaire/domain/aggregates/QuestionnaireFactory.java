package knbit.events.bc.interest.questionnaire.domain.aggregates;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionData;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionnaireId;

import java.util.List;

/**
 * Created by novy on 26.05.15.
 */
public class QuestionnaireFactory {

    public static Questionnaire newQuestionnaire(QuestionnaireId questionnaireId, EventId eventId, List<QuestionData> questions) {
        return new Questionnaire(questionnaireId, eventId, questions);
    }

}

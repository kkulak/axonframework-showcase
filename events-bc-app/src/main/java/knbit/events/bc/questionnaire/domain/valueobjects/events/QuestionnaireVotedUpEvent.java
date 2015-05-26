package knbit.events.bc.questionnaire.domain.valueobjects.events;

import knbit.events.bc.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import knbit.events.bc.questionnaire.domain.valueobjects.question.AnsweredQuestion;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 26.05.15.
 */

@Accessors(fluent = true)
@Value
public class QuestionnaireVotedUpEvent implements QuestionnaireVotedEvent {

    private final QuestionnaireId questionnaireId;
    private final Attendee attendee;
    private final List<AnsweredQuestion> answeredQuestions;

}

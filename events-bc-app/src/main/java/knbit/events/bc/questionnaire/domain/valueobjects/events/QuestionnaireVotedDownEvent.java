package knbit.events.bc.questionnaire.domain.valueobjects.events;

import knbit.events.bc.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 26.05.15.
 */

@Accessors(fluent = true)
@Value
public class QuestionnaireVotedDownEvent {

    private final QuestionnaireId questionnaireId;
    private final Attendee attendee;
}

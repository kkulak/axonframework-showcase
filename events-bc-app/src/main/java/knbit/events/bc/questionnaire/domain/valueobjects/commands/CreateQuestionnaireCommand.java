package knbit.events.bc.questionnaire.domain.valueobjects.commands;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 26.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class CreateQuestionnaireCommand {

    private final QuestionnaireId questionnaireId;
    private final EventId eventId;
}

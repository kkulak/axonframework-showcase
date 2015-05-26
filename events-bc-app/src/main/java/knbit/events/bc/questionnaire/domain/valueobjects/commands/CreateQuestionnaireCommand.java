package knbit.events.bc.questionnaire.domain.valueobjects.commands;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.questionnaire.domain.valueobjects.question.QuestionData;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 26.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class CreateQuestionnaireCommand {

    private final QuestionnaireId questionnaireId;
    private final EventId eventId;
    private final List<QuestionData> questions;
}

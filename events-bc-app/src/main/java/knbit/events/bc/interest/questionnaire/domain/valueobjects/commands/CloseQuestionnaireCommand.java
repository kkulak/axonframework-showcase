package knbit.events.bc.interest.questionnaire.domain.valueobjects.commands;

import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 29.05.15.
 */

@Accessors(fluent = true)
@Value
public class CloseQuestionnaireCommand {

    private final QuestionnaireId questionnaireId;
}

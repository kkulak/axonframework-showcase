package knbit.events.bc.questionnaire.domain.builders;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.questionnaire.domain.valueobjects.commands.CreateQuestionnaireCommand;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 26.05.15.
 */

@Setter
@Accessors(fluent = true)
@NoArgsConstructor(staticName = "instance")
public class CreateQuestionnaireCommandBuilder {

    private QuestionnaireId questionnaireId = QuestionnaireId.of("questionnaireId");
    private EventId eventId = EventId.of("eventId");


    public CreateQuestionnaireCommand build() {
        return CreateQuestionnaireCommand.of(questionnaireId, eventId);
    }

}
package knbit.events.bc.questionnaire.domain.builders;

import knbit.events.bc.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.questionnaire.domain.valueobjects.events.QuestionnaireVotedDownEvent;
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
public class QuestionnaireVotedDownEventBuilder {

    private QuestionnaireId questionnaireId = QuestionnaireId.of("questionId");
    private Attendee attendee = Attendee.of("firstname", "lastname");

    public QuestionnaireVotedDownEvent build() {
        return new QuestionnaireVotedDownEvent(questionnaireId, attendee);
    }
}

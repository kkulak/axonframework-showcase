package knbit.events.bc.interest.questionnaire.domain.valueobjects.events;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionData;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 26.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class QuestionnaireCreatedEvent {

    private final QuestionnaireId questionnaireId;
    private final EventId eventId;
    private final List<QuestionData> questions;

}

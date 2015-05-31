package knbit.events.bc.interest.domain.valueobjects.events;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.question.Question;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 31.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class QuestionnaireAddedEvent {

    private final EventId eventId;
    private final List<Question> questions;
}

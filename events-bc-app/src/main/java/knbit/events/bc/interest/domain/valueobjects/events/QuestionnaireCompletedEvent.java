package knbit.events.bc.interest.domain.valueobjects.events;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.interest.domain.valueobjects.question.answer.AnsweredQuestion;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 31.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class QuestionnaireCompletedEvent {

    private final EventId eventId;
    private final Attendee attendee;
    private final List<AnsweredQuestion> answeredQuestions;
}

package knbit.events.bc.interest.domain.valueobjects.events;

import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.question.Question;
import knbit.events.bc.interest.domain.valueobjects.question.answer.AnsweredQuestion;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 22.08.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QuestionnaireEvents {

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class Added {

        EventId eventId;
        List<Question> questions;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class CompletedByAttendee {

        EventId eventId;
        Attendee attendee;
        List<AnsweredQuestion> answeredQuestions;
    }
}

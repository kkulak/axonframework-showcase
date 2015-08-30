package knbit.events.bc.interest.domain.valueobjects.commands;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.question.QuestionData;
import knbit.events.bc.interest.domain.valueobjects.submittedanswer.AttendeeAnswer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 22.08.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QuestionnaireCommands {

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class Add {

        EventId eventId;
        List<QuestionData> questions;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class Complete {

        EventId eventId;
        AttendeeAnswer answer;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class End {

        EventId eventId;
    }
}

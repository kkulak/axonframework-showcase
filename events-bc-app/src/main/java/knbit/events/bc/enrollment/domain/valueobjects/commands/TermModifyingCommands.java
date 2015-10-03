package knbit.events.bc.enrollment.domain.valueobjects.commands;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.TermId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 03.10.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TermModifyingCommands {

    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    public static class AssignLecturer {

        EventId eventId;
        TermId termId;
        String firstName;
        String lastName;
    }


    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    public static class SetParticipantLimit {

        EventId eventId;
        TermId termId;
        int participantLimit;
    }
}

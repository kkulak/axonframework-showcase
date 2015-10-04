package knbit.events.bc.enrollment.domain.valueobjects.commands;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 03.10.15.
 */

public interface TermModifyingCommands {

    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    class AssignLecturer {

        EventId eventId;
        TermId termId;
        String firstName;
        String lastName;
    }


    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    class SetParticipantLimit {

        EventId eventId;
        TermId termId;
        int participantLimit;
    }
}

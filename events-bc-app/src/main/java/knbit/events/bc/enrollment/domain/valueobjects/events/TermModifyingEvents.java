package knbit.events.bc.enrollment.domain.valueobjects.events;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantLimit;
import knbit.events.bc.enrollment.domain.valueobjects.TermId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 03.10.15.
 */

public interface TermModifyingEvents {

    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    class LecturerAssigned implements TermEvent {

        EventId eventId;
        TermId termId;
        Lecturer lecturer;
    }


    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    class ParticipantLimitSet implements TermEvent {

        EventId eventId;
        TermId termId;
        ParticipantLimit participantLimit;
    }
}

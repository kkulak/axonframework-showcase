package knbit.events.bc.enrollment.domain.valueobjects.events;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.TermId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 03.10.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TermModifyingEvents {

    @Value(staticConstructor = "of")
    @Accessors(fluent = true)
    public static class LecturerAssigned implements TermEvent {

        EventId eventId;
        TermId termId;
        Lecturer lecturer;
    }
}

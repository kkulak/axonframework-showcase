package knbit.events.bc.enrollment.domain.valueobjects.events;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.Participant;
import knbit.events.bc.enrollment.domain.valueobjects.TermId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 03.10.15.
 */

public interface EnrollmentEvents {

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class ParticipantEnrolledForTerm {

        EventId eventId;
        TermId termId;
        Participant participant;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class ParticipantDisenrolledFromTerm {

        EventId eventId;
        TermId termId;
        Participant participant;
    }
}

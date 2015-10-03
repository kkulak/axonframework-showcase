package knbit.events.bc.enrollment.domain.valueobjects.commands;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.Participant.ParticipantId;
import knbit.events.bc.enrollment.domain.valueobjects.TermId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 03.10.15.
 */

public interface EnrollmentCommands {

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class EnrollFor {

        EventId eventId;
        TermId termId;
        ParticipantId participantId;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class DissenrollFrom {

        EventId eventId;
        TermId termId;
        ParticipantId participantId;
    }
}

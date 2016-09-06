package knbit.events.bc.enrollment.domain.valueobjects.commands;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
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
        MemberId memberId;
    }

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    class DissenrollFrom {

        EventId eventId;
        TermId termId;
        MemberId memberId;
    }
}

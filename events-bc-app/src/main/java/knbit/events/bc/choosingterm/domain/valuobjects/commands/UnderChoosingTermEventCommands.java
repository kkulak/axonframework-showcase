package knbit.events.bc.choosingterm.domain.valuobjects.commands;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.TermClosure;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.List;

/**
 * Created by novy on 22.08.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UnderChoosingTermEventCommands {

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class Create {

        EventId eventId;
        EventDetails eventDetails;
    }


    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class TransitToEnrollment {

        EventId eventId;
        Collection<TermClosure> termClosures;
    }
}

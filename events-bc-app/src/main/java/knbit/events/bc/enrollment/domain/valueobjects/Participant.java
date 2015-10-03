package knbit.events.bc.enrollment.domain.valueobjects;

import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 03.10.15.
 */


@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class Participant {

    ParticipantId participantId;

    @Accessors(fluent = true)
    @Value(staticConstructor = "of")
    public static class ParticipantId {

        String value;
    }
}


package knbit.events.bc.enrollment.domain.valueobjects;

import knbit.events.bc.common.domain.UUIDBasedIdentifier;

/**
 * Created by novy on 03.10.15.
 */
public class ParticipantId extends UUIDBasedIdentifier {

    public ParticipantId() {
        super();
    }

    protected ParticipantId(String id) {
        super(id);
    }

    public static ParticipantId of(String id) {
        return new ParticipantId(id);
    }
}

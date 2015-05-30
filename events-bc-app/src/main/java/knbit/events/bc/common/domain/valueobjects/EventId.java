package knbit.events.bc.common.domain.valueobjects;

import knbit.events.bc.common.domain.UUIDBasedIdentifier;

/**
 * Created by novy on 07.05.15.
 */
public class EventId extends UUIDBasedIdentifier {

    public EventId() {
        super();
    }

    protected EventId(String id) {
        super(id);
    }

    public static EventId of(String id) {
        return new EventId(id);
    }
}

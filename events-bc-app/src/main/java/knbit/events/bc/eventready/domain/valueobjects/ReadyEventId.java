package knbit.events.bc.eventready.domain.valueobjects;

import knbit.events.bc.common.domain.UUIDBasedIdentifier;

/**
 * Created by novy on 30.10.15.
 */
public class ReadyEventId extends UUIDBasedIdentifier {

    public ReadyEventId() {
        super();
    }

    protected ReadyEventId(String id) {
        super(id);
    }

    public static ReadyEventId of(String id) {
        return new ReadyEventId(id);
    }
}

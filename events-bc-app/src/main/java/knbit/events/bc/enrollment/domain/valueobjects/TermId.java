package knbit.events.bc.enrollment.domain.valueobjects;

import knbit.events.bc.common.domain.UUIDBasedIdentifier;

/**
 * Created by novy on 03.10.15.
 */
public class TermId extends UUIDBasedIdentifier {

    public TermId() {
        super();
    }

    protected TermId(String id) {
        super(id);
    }

    public static TermId of(String id) {
        return new TermId(id);
    }
}

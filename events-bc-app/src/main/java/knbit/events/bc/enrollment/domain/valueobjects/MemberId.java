package knbit.events.bc.enrollment.domain.valueobjects;

import knbit.events.bc.common.domain.UUIDBasedIdentifier;

/**
 * Created by novy on 03.10.15.
 */
public class MemberId extends UUIDBasedIdentifier {

    public MemberId() {
        super();
    }

    protected MemberId(String id) {
        super(id);
    }

    public static MemberId of(String id) {
        return new MemberId(id);
    }
}

package knbit.events.bc.enrollment.domain.valueobjects;

import com.google.common.base.Preconditions;
import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;

/**
 * Created by novy on 03.10.15.
 */
public class ParticipantLimit {

    int value;

    public static ParticipantLimit of(int value) {
        return new ParticipantLimit(value);
    }

    public static ParticipantLimit of(Capacity capacity) {
        return new ParticipantLimit(capacity.value());
    }

    private ParticipantLimit(int value) {
        Preconditions.checkArgument(value > 0, "Limit must be grater than 0");
        this.value = value;
    }

    public boolean fitsCapacity(Capacity capacity) {
        return true;
    }
}

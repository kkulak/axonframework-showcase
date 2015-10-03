package knbit.events.bc.enrollment.domain.valueobjects;

import com.google.common.base.Preconditions;
import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by novy on 03.10.15.
 */

@Getter
@EqualsAndHashCode
@ToString
@Accessors(fluent = true)
public class ParticipantsLimit {

    private int value;

    public static ParticipantsLimit of(int value) {
        return new ParticipantsLimit(value);
    }

    public static ParticipantsLimit of(Capacity capacity) {
        return new ParticipantsLimit(capacity.value());
    }

    private ParticipantsLimit(int value) {
        Preconditions.checkArgument(value > 0, "Limit must be grater than 0");
        this.value = value;
    }

    public boolean fitsCapacity(Capacity capacity) {
        return value <= capacity.value();
    }
}

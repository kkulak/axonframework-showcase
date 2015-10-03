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
        return value <= capacity.value();
    }
}

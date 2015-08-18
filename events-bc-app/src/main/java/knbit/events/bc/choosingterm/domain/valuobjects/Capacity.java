package knbit.events.bc.choosingterm.domain.valuobjects;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by novy on 19.08.15.
 */

@Getter
@EqualsAndHashCode
@ToString
@Accessors(fluent = true)
public class Capacity {

    int value;

    public static Capacity of(int value) {
        return new Capacity(value);
    }

    private Capacity(int value) {
        Preconditions.checkArgument(value > 0, "Capacity must be grater than 0");
        this.value = value;
    }
}

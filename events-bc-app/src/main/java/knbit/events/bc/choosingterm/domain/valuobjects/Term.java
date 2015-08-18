package knbit.events.bc.choosingterm.domain.valuobjects;

import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 19.08.15.
 */

@Value(staticConstructor = "of")
@Accessors(fluent = true)
public class Term {

    EventDuration duration;
    Capacity capacity;
    Location location;

    public boolean overlaps(Term another) {
        return location.equals(another.location()) && duration.overlaps(another.duration());
    }
}

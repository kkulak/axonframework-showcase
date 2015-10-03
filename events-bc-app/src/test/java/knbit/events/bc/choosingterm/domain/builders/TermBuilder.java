package knbit.events.bc.choosingterm.domain.builders;

import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import knbit.events.bc.choosingterm.domain.valuobjects.Location;
import knbit.events.bc.choosingterm.domain.valuobjects.Term;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * Created by novy on 03.10.15.
 */

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "instance")
public class TermBuilder {

    private EventDuration duration = EventDuration.of(
            new DateTime(2001, 9, 11, 0, 0), Duration.standardHours(2)
    );
    private Capacity capacity = Capacity.of(100);
    private Location location = Location.of("default location");

    public Term build() {
        return Term.of(duration, capacity, location);
    }

    public static Term defaultTerm() {
        return TermBuilder.instance().build();
    }
}
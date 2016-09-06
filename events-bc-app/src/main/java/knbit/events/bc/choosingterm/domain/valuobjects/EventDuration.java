package knbit.events.bc.choosingterm.domain.valuobjects;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;


/**
 * Created by novy on 19.08.15.
 */

@Getter
@EqualsAndHashCode
@ToString
@Accessors(fluent = true)
public class EventDuration {

    DateTime start;
    Duration duration;

    public static EventDuration of(DateTime start, Duration duration) {
        return new EventDuration(start, duration);
    }

    public static EventDuration of(DateTime start, DateTime end) {
        return new EventDuration(start, new Duration(start, end));
    }

    private EventDuration(DateTime start, Duration duration) {
        Preconditions.checkNotNull(start);
        Preconditions.checkNotNull(duration);

        this.start = start;
        this.duration = duration;
    }

    private Interval interval() {
        return new Interval(start, duration);
    }

    public boolean overlaps(EventDuration another) {
        return interval().overlaps(another.interval());
    }

    public DateTime end() {
        return start.withDurationAdded(duration, 1);
    }
}

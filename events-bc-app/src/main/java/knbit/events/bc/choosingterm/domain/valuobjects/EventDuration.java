package knbit.events.bc.choosingterm.domain.valuobjects;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.threeten.extra.Interval;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Created by novy on 19.08.15.
 */

@Getter
@EqualsAndHashCode
@ToString
@Accessors(fluent = true)
public class EventDuration {

    LocalDateTime start;
    Duration duration;

    public static EventDuration of(LocalDateTime start, Duration duration) {
        return new EventDuration(start, duration);
    }

    private EventDuration(LocalDateTime start, Duration duration) {
        Preconditions.checkNotNull(start);
        Preconditions.checkNotNull(duration);

        this.start = start;
        this.duration = duration;
    }

    private Interval interval() {
        return Interval.of(start.toInstant(ZoneOffset.UTC), duration);
    }

    public boolean overlaps(EventDuration another) {
        return interval().overlaps(another.interval());
    }
}

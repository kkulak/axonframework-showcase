package knbit.events.bc.common.domain.valueobjects;

import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.domain.enums.EventType;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 30.05.15.
 */
@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class EventDetails {

    private Name name;
    private Description description;
    private EventType type;
    private EventFrequency frequency;
}

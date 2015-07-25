package knbit.events.bc.interest.builders;

import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.Name;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 30.05.15.
 */

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "instance")
public class EventDetailsBuilder {

    private Name name = Name.of("name");
    private Description description = Description.of("desc");
    private EventType type = EventType.WORKSHOP;
    private EventFrequency frequency = EventFrequency.ONE_OFF;


    public EventDetails build() {
        return EventDetails.of(name, description, type, frequency);
    }

}

package knbit.events.bc.interest.builders;

import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.*;
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
    private URL imageUrl = URL.of("https://www.google.pl/");
    private Section section = Section.of("0", "Idea Factory");

    public EventDetails build() {
        return EventDetails.of(name, description, type, imageUrl, section);
    }

    public static EventDetails defaultEventDetails() {
        return EventDetailsBuilder.instance().build();
    }

}

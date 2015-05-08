package knbit.events.bc.event.domain.valueobjects;

import lombok.Value;
import lombok.experimental.Accessors;

@Value(staticConstructor = "of")
@Accessors(fluent = true)
public class Description {

    private final String value;

}

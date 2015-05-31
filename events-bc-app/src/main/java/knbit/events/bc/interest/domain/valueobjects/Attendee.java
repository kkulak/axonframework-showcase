package knbit.events.bc.interest.domain.valueobjects;

import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 25.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class Attendee {

    private final String firstName;
    private final String lastName;
}

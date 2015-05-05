package knbit.events.bc.domain.proposing.event.valueobjects;

import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 05.05.15.
 */

@Value(staticConstructor = "of")
@Accessors
public class Name {

    private final String name;

}

package knbit.events.bc.axon.dummy;

import lombok.Value;

/**
 * Created by novy on 05.05.15.
 */

@Value
public class FooCreatedEvent {

    private final FooId fooId;
    private final String name;
}

package knbit.events.bc.axon.dummy;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

/**
 * Created by novy on 05.05.15.
 */
public class FooAggregate extends AbstractAnnotatedAggregateRoot {

    @AggregateIdentifier
    private FooId id;

    private String name;

    private FooAggregate() {
    }

    public FooAggregate(FooId id, String name) {
        apply(
                new FooCreatedEvent(id, name)
        );
    }

    @EventSourcingHandler
    public void on(FooCreatedEvent event) {
        this.id = event.getFooId();
        this.name = event.getName();
    }
}

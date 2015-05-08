package knbit.events.bc.event.infrastructure.config;

import knbit.events.bc.event.domain.aggregates.AbstractEvent;
import knbit.events.bc.event.domain.aggregates.CyclicEvent;
import knbit.events.bc.event.domain.aggregates.OneOffEvent;
import knbit.events.bc.event.domain.valueobjects.events.CyclicEventCreated;
import knbit.events.bc.event.domain.valueobjects.events.OneOffEventCreated;
import knbit.events.bc.event.infrastructure.exception.IllegalEventStoreStateException;
import org.axonframework.domain.DomainEventMessage;
import org.axonframework.eventsourcing.AbstractAggregateFactory;

public class AxonEventFactory extends AbstractAggregateFactory<AbstractEvent> {

    @Override
    protected AbstractEvent doCreateAggregate(Object aggregateIdentifier, DomainEventMessage firstEvent) {
        if(OneOffEventCreated.class.isAssignableFrom(firstEvent.getPayloadType())) {
            return new OneOffEvent();
        } else if(CyclicEventCreated.class.isAssignableFrom(firstEvent.getPayloadType())) {
            return new CyclicEvent();
        }
        throw new IllegalEventStoreStateException("Cannot instantiate subclass of AbstractEvent!");
    }

    @Override
    public String getTypeIdentifier() {
        return AbstractEvent.class.getSimpleName();
    }

    @Override
    public Class<AbstractEvent> getAggregateType() {
        return AbstractEvent.class;
    }

}

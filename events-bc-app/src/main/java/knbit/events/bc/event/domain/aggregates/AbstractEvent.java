package knbit.events.bc.event.domain.aggregates;

import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.event.domain.valueobjects.Description;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.event.domain.enums.EventState;
import knbit.events.bc.event.domain.valueobjects.Name;
import knbit.events.bc.common.domain.enums.EventType;

public abstract class AbstractEvent extends IdentifiedDomainAggregateRoot<EventId> {
    protected Name name;
    protected Description description;
    protected EventState state;
    protected EventType type;

    protected AbstractEvent() { }

}

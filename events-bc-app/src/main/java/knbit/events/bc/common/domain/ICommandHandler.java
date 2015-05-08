package knbit.events.bc.common.domain;

import org.axonframework.eventsourcing.AbstractEventSourcedAggregateRoot;
import org.axonframework.repository.Repository;

public interface ICommandHandler<T extends AbstractEventSourcedAggregateRoot> {

    void setRepository(Repository<T> repository);

}

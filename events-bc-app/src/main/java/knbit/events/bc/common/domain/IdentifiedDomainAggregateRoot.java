package knbit.events.bc.common.domain;

import knbit.events.bc.common.domain.valueobjects.EventDetails;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

/**
 * Created by novy on 05.05.15.
 */
public abstract class IdentifiedDomainAggregateRoot<ID extends DomainIdentifier> extends AbstractAnnotatedAggregateRoot<ID>
        implements IdentifiedObject<ID> {

    @AggregateIdentifier
    protected ID id;

    @Override
    public ID id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !this.getClass().isInstance(o)) return false;

        IdentifiedDomainAggregateRoot that = (IdentifiedDomainAggregateRoot) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}

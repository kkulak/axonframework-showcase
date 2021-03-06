package knbit.events.bc.common.domain;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;

/**
 * Created by novy on 05.05.15.
 */
public class IdentifiedDomainEntity<ID extends DomainIdentifier> extends AbstractAnnotatedEntity
        implements DomainIdentifier<ID> {

    protected ID id;

    @Override
    public ID value() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !this.getClass().isInstance(o)) return false;

        IdentifiedDomainEntity that = (IdentifiedDomainEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

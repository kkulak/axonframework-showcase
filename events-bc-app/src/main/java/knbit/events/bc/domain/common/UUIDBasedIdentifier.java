package knbit.events.bc.domain.common;

import java.util.UUID;

/**
 * Created by novy on 05.05.15.
 */

public class UUIDBasedIdentifier implements DomainIdentifier<String> {

    private final String id;

    public UUIDBasedIdentifier() {
        this.id = randomUUIDString();
    }

    protected UUIDBasedIdentifier(String id) {
        this.id = id;
    }

    private String randomUUIDString() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UUIDBasedIdentifier that = (UUIDBasedIdentifier) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "UUIDBasedId{" +
                "id='" + id + '\'' +
                '}';
    }
}

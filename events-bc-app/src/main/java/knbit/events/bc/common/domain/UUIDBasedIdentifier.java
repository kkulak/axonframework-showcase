package knbit.events.bc.common.domain;

import java.util.UUID;

/**
 * Created by novy on 05.05.15.
 */

public class UUIDBasedIdentifier implements DomainIdentifier<String> {

    private final String value;

    public UUIDBasedIdentifier() {
        this.value = randomUUIDString();
    }

    protected UUIDBasedIdentifier(String value) {
        this.value = value;
    }

    private String randomUUIDString() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UUIDBasedIdentifier that = (UUIDBasedIdentifier) o;

        if (!value.equals(that.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "UUIDBasedId{" +
                "value='" + value + '\'' +
                '}';
    }
}

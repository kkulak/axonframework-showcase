package knbit.events.bc.common.domain.valueobjects;

import knbit.events.bc.common.domain.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Optional;

/**
 * Created by novy on 30.05.15.
 */
@AllArgsConstructor(staticName = "of")
@Accessors(fluent = true)
@EqualsAndHashCode
public class EventDetails {

    @Getter Name name;
    @Getter Description description;
    @Getter EventType type;
    URL imageUrl;
    Section section;

    public Optional<URL> imageUrl() {
        return Optional.ofNullable(imageUrl);
    }

    public Optional<Section> section() {
        return Optional.ofNullable(section);
    }

}

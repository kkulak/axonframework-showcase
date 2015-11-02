package knbit.events.bc.common.domain.valueobjects;

import lombok.Value;
import lombok.experimental.Accessors;

@Value(staticConstructor = "of")
@Accessors(fluent = true)
public class Section {
    String id;
    String name;
}

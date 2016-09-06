package knbit.events.bc.enrollment.domain.valueobjects;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Optional;

/**
 * Created by novy on 03.10.15.
 */

@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
public class Lecturer {

    @Getter private final String name;
    private final String id;

    public static Lecturer of(String name, String id) {
        return new Lecturer(name, id);
    }

    private Lecturer(String name, String id) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name), "Name cannot be empty!");

        this.name = name;
        this.id = id;
    }

    public Optional<String> id() {
        return Optional.ofNullable(id);
    }

}

package knbit.events.bc.enrollment.domain.valueobjects;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by novy on 03.10.15.
 */


@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
public class Lecturer {

    private final String firstName;
    private final String lastName;

    public static Lecturer of(String firstName, String lastName) {
        return new Lecturer(firstName, lastName);
    }

    private Lecturer(String firstName, String lastName) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(firstName), "First name cannot be empty!");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(lastName), "Last name cannot be empty!");

        this.firstName = firstName;
        this.lastName = lastName;
    }
}

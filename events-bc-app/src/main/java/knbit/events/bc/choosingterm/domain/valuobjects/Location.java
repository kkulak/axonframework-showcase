package knbit.events.bc.choosingterm.domain.valuobjects;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by novy on 19.08.15.
 */

@Getter
@EqualsAndHashCode
@ToString
public class Location {

    String value;

    public static Location of(String value) {
        return new Location(value);
    }

    private Location(String value) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(value), "Location cannot be empty!");

        this.value = value;
    }
}

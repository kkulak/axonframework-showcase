package knbit.events.bc.common.domain.valueobjects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.apache.commons.validator.routines.UrlValidator;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class URL {
    String value;

    public static URL of(String value) {
        checkArgument(isValid(value), "Malformed url address");
        return new URL(value);
    }

    public static boolean isValid(String value) {
        return new UrlValidator().isValid(value);
    }

}

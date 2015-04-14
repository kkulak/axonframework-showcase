package knbit.events.bc.announcement.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by novy on 03.04.15.
 */
public enum Publishers {

    TWITTER,
    FACEBOOK,
    GOOGLE_GROUP,
    IIET_BOARD;

    public static Collection<String> stringValues() {
        return Arrays.asList(values())
                .stream()
                .map(publisher -> publisher.toString().toLowerCase())
                .collect(Collectors.toList());
    }

    public static Collection<Publishers> fromStringValues(Collection<String> stringValues) {
        return stringValues
                .stream()
                .map(publisherString -> Publishers.valueOf(publisherString.toUpperCase()))
                .collect(Collectors.toList());
    }

}

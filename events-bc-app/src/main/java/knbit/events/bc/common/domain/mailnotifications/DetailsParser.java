package knbit.events.bc.common.domain.mailnotifications;

import com.google.common.collect.Maps;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.Section;
import knbit.events.bc.common.domain.valueobjects.URL;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by novy on 13.12.15.
 */
class DetailsParser {

    public static Map<String, String> parse(EventDetails details) {
        final HashMap<String, String> data = Maps.newHashMap();

        data.put("eventName", details.name().value());
        data.put("eventDescription", details.description().value());
        data.put("eventType", details.type().name());

        final Optional<URL> maybeUrl = details.imageUrl();
        maybeUrl.ifPresent(url -> data.put("imageUrl", url.value()));

        final Optional<Section> maybeSection = details.section();
        maybeSection.ifPresent(section -> data.put("section", section.name()));

        return data;
    }

    public static Map<String, String> parse(EventReadyDetails details) {
        final Map<String, String> parsedDetails = parse(details.eventDetails());
        parsedDetails.put("start", details.duration().start().toString());
        parsedDetails.put("location", details.location().value());
        parsedDetails.put("lecturers", asString(details.lecturers()));

        return parsedDetails;
    }

    private static String asString(Collection<Lecturer> lecturers) {
        return lecturers.stream()
                .map(Lecturer::name)
                .collect(Collectors.joining(", "));
    }
}

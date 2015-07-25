package knbit.events.bc.interest.domain.valueobjects.commands;

import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.Value;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import java.util.Optional;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class StartSurveyingInterestCommand {

    private final EventId eventId;
    private final Optional<Integer> minimalInterestThreshold;
    private final Optional<DateTime> endingSurveyDate;
}

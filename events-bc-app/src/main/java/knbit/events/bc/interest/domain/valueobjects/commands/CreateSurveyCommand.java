package knbit.events.bc.interest.domain.valueobjects.commands;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.SurveyId;
import lombok.Value;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import java.util.Optional;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Value
public class CreateSurveyCommand {

    private final SurveyId surveyId;
    private final EventId eventId;
    private final Optional<Integer> minimalInterestThreshold;
    private final Optional<DateTime> endingSurveyDate;
}

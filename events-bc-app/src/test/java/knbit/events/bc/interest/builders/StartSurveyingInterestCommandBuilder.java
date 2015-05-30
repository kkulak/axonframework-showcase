package knbit.events.bc.interest.builders;


import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.commands.StartSurveyingInterestCommand;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import java.util.Optional;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "instance")
public class StartSurveyingInterestCommandBuilder {

    private EventId eventId = EventId.of("eventId");
    private Optional<Integer> minimalInterestThreshold = Optional.empty();
    private Optional<DateTime> endingSurveyDate = Optional.empty();

    public StartSurveyingInterestCommand build() {
        return StartSurveyingInterestCommand.of(eventId, minimalInterestThreshold, endingSurveyDate);
    }

}

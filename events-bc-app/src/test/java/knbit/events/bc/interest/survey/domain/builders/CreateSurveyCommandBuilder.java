package knbit.events.bc.interest.survey.domain.builders;


import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.domain.valueobjects.commands.CreateSurveyCommand;
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
public class CreateSurveyCommandBuilder {

    private SurveyId surveyId = SurveyId.of("surveyId");
    private EventId eventId = EventId.of("eventId");
    private Optional<Integer> minimalInterestThreshold = Optional.empty();
    private Optional<DateTime> endingSurveyDate = Optional.empty();

    public CreateSurveyCommand build() {
        return new CreateSurveyCommand(surveyId, eventId, minimalInterestThreshold, endingSurveyDate);
    }

}

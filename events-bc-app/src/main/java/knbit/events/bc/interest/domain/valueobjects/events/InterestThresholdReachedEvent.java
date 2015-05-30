package knbit.events.bc.interest.domain.valueobjects.events;

import knbit.events.bc.interest.domain.valueobjects.SurveyId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Value
public class InterestThresholdReachedEvent {

    private final SurveyId surveyId;
}

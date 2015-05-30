package knbit.events.bc.interest.domain.valueobjects.events;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.survey.domain.policies.InterestPolicy;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 30.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class SurveyingStartedEvent {

    private final EventId eventId;
    private final InterestPolicy interestPolicy;

}

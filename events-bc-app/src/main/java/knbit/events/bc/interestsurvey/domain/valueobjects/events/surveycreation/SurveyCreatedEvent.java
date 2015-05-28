package knbit.events.bc.interestsurvey.domain.valueobjects.events.surveycreation;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interestsurvey.domain.policies.InterestPolicy;
import knbit.events.bc.interestsurvey.domain.valueobjects.SurveyId;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class SurveyCreatedEvent {

    private final SurveyId surveyId;
    private final EventId eventId;
    private final InterestPolicy interestPolicy;
}

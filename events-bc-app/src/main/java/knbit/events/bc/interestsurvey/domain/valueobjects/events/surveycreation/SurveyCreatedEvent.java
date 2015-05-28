package knbit.events.bc.interestsurvey.domain.valueobjects.events.surveycreation;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interestsurvey.domain.policies.InterestThresholdPolicy;
import knbit.events.bc.interestsurvey.domain.valueobjects.SurveyId;
import knbit.events.bc.interestsurvey.domain.valueobjects.commands.CreateSurveyCommand;
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
    private final InterestThresholdPolicy interestThresholdPolicy;
}

package knbit.events.bc.interest.domain.valueobjects.events.surveystarting;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.survey.domain.policies.InterestPolicy;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * Created by novy on 30.05.15.
 */

@Accessors(fluent = true)
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class SurveyingInterestStartedEvent {

    private final EventId eventId;
    private final InterestPolicy interestPolicy;

    public static SurveyingInterestStartedEvent of(EventId eventId, InterestPolicy interestPolicy) {
        return new SurveyingInterestStartedEvent(eventId, interestPolicy);
    }

}

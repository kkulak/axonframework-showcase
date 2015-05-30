package knbit.events.bc.interest.domain.valueobjects.events.surveycreation;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.survey.domain.policies.InterestPolicy;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
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

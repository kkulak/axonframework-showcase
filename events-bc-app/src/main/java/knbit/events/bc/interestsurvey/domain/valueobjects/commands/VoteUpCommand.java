package knbit.events.bc.interestsurvey.domain.valueobjects.commands;

import knbit.events.bc.interestsurvey.domain.valueobjects.SurveyId;
import knbit.events.bc.questionnaire.domain.valueobjects.Attendee;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Value
public class VoteUpCommand {

    private final SurveyId surveyId;
    private final Attendee attendee;
}

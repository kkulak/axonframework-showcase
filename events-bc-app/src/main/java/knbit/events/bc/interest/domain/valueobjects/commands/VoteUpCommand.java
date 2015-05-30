package knbit.events.bc.interest.domain.valueobjects.commands;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Value
public class VoteUpCommand {

    private final EventId eventId;
    private final Attendee attendee;
}

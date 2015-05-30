package knbit.events.bc.interest.domain.valueobjects.events;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class SurveyVotedDownEvent {

    private final EventId eventId;
    private final Attendee attendee;
}

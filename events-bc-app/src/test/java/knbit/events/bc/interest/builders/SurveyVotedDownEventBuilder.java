package knbit.events.bc.interest.builders;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyVotedDownEvent;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "instance")
public class SurveyVotedDownEventBuilder {

    private EventId eventId = EventId.of("eventId");
    private Attendee attendee = Attendee.of("firstname", "lastname");

    public SurveyVotedDownEvent build() {
        return SurveyVotedDownEvent.of(eventId, attendee);
    }

}

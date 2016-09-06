package knbit.events.bc.interest.builders;

import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import knbit.events.bc.interest.domain.valueobjects.commands.SurveyCommands;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "instance")
public class VoteUpCommandBuilder {

    private EventId eventId = EventId.of("eventId");
    private Attendee attendee = Attendee.of(
            MemberId.of("memberId")
    );

    public SurveyCommands.VoteUp build() {
        return new SurveyCommands.VoteUp(eventId, attendee);
    }

}

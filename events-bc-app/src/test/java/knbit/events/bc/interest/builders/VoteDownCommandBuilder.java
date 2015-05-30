package knbit.events.bc.interest.builders;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.commands.VoteDownCommand;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "instance")
public class VoteDownCommandBuilder {

    private EventId eventId = EventId.of("eventId");
    private Attendee attendee = Attendee.of("firstname", "lastname");

    public VoteDownCommand build() {
        return new VoteDownCommand(eventId, attendee);
    }

}

package knbit.events.bc.interest.web.forms;

import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.commands.VoteDownCommand;
import knbit.events.bc.interest.domain.valueobjects.commands.VoteUpCommand;

public class VoteCommandFactory {

    public static Object create(String eventId, AttendeeDTO attendeeDTO, VoteDTO type) {
        final Attendee attendee = Attendee.of(attendeeDTO.getFirstName(), attendeeDTO.getLastName());
        switch (type) {
            case POSITIVE:
                return new VoteUpCommand(EventId.of(eventId), attendee);
            case NEGATIVE:
                return new VoteDownCommand(EventId.of(eventId), attendee);
            default:
                throw new IllegalArgumentException();
        }
    }

}

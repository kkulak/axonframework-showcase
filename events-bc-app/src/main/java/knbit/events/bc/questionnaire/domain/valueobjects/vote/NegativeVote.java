package knbit.events.bc.questionnaire.domain.valueobjects.vote;

import knbit.events.bc.questionnaire.domain.valueobjects.Attendee;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 25.05.15.
 */

@Accessors(fluent = true)
@Value
public class NegativeVote {

    private final Attendee attendee;

    public NegativeVote(Attendee attendee) {
        this.attendee = attendee;
    }
}

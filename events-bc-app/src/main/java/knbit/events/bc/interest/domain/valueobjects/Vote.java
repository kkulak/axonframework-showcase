package knbit.events.bc.interest.domain.valueobjects;

import knbit.events.bc.interest.domain.enums.VoteType;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 30.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class Vote {

    private final Attendee attendee;
    private final VoteType voteType;
}

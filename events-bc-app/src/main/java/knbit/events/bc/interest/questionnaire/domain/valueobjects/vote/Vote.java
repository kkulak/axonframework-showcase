package knbit.events.bc.interest.questionnaire.domain.valueobjects.vote;

import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by novy on 25.05.15.
 */

@Accessors(fluent = true)
@Getter
@RequiredArgsConstructor
public class Vote {

    private final Attendee attendee;

}

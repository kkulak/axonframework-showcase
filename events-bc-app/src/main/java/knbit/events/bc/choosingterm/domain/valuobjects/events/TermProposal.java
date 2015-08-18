package knbit.events.bc.choosingterm.domain.valuobjects.events;

import knbit.events.bc.choosingterm.domain.valuobjects.Capacity;
import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 19.08.15.
 */

@Value
@Accessors(fluent = true)
public class TermProposal {

    EventDuration duration;
    Capacity capacity;
}

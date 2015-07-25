package knbit.events.bc.interest.domain.valueobjects.commands;

import knbit.events.bc.common.domain.valueobjects.EventId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class EndSurveyingInterestCommand {

    private final EventId eventId;
}

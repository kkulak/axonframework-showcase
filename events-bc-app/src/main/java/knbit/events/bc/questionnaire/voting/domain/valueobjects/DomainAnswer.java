package knbit.events.bc.questionnaire.voting.domain.valueobjects;

import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 26.05.15.
 */

@Accessors(fluent = true)
@Value
public class DomainAnswer {

    private final String value;
}

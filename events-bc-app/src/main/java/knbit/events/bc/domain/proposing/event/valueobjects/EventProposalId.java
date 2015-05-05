package knbit.events.bc.domain.proposing.event.valueobjects;

import knbit.events.bc.domain.common.DomainIdentifier;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 05.05.15.
 */

@Value(staticConstructor = "of")
@Accessors
public class EventProposalId implements DomainIdentifier<String> {

    private final String id;
}

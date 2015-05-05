package knbit.events.bc.domain.proposing.event.aggregates;

import knbit.events.bc.domain.proposing.event.valueobjects.Description;
import knbit.events.bc.domain.proposing.event.valueobjects.EventProposalId;
import knbit.events.bc.domain.proposing.event.valueobjects.Name;

/**
 * Created by novy on 05.05.15.
 */
public class EventProposalFactory {

    public static EventProposal newEventProposal(EventProposalId anId, String name, String description) {
        return new EventProposal(
                anId, Name.of(name), Description.of(description)
        );
    }

}

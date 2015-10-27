package knbit.events.bc.eventproposal.domain.aggregates;

import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.Name;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;

/**
 * Created by novy on 05.05.15.
 */
public class EventProposalFactory {

    public static EventProposal newEventProposal(
            EventProposalId anId, String name, String description, EventType eventType) {
        return new EventProposal(
                anId, Name.of(name), Description.of(description), eventType
        );
    }

}

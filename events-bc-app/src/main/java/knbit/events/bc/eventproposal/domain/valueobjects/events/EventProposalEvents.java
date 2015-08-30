package knbit.events.bc.eventproposal.domain.valueobjects.events;

import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.Name;
import knbit.events.bc.eventproposal.domain.enums.ProposalState;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 22.08.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EventProposalEvents {

    @Value
    @Accessors(fluent = true)
    public static class EventProposed {

        EventProposalId eventProposalId;
        Name name;
        Description description;
        EventType eventType;
        EventFrequency eventFrequency;
        ProposalState proposalState;
    }

    public interface ProposalStateChanged {

        EventProposalId eventProposalId();

        ProposalState state();
    }

    @Value
    @Accessors(fluent = true)
    public static class ProposalAccepted implements ProposalStateChanged {

        EventProposalId eventProposalId;
        EventType eventType;
        ProposalState state;
    }

    @Value
    @Accessors(fluent = true)
    public static class ProposalRejected implements ProposalStateChanged {

        EventProposalId eventProposalId;
        ProposalState state;
    }
}

package knbit.events.bc.eventproposal.domain.valueobjects.commands;

import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 22.08.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EventProposalCommands {

    @Value
    @Accessors(fluent = true)
    public static class ProposeEvent {

        EventProposalId eventProposalId;
        String name;
        String description;
        EventType eventType;

    }

    @Value
    @Accessors(fluent = true)
    public static class AcceptProposal {

        EventProposalId eventProposalId;
    }

    @Value
    @Accessors(fluent = true)
    public static class RejectProposal {

        EventProposalId eventProposalId;
    }
}

package knbit.events.bc.eventproposal.domain.valueobjects.events;

import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.Name;
import knbit.events.bc.common.domain.valueobjects.URL;
import knbit.events.bc.eventproposal.domain.enums.ProposalState;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Optional;

/**
 * Created by novy on 22.08.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EventProposalEvents {

    @AllArgsConstructor
    @Accessors(fluent = true)
    public static class EventProposed {

        @Getter EventProposalId eventProposalId;
        @Getter Name name;
        @Getter Description description;
        @Getter EventType eventType;
        URL imageUrl;
        @Getter ProposalState proposalState;

        public Optional<URL> imageUrl() {
            return Optional.ofNullable(imageUrl);
        }

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

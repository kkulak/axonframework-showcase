package knbit.events.bc.eventproposal.domain.builders;

import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.domain.valueobjects.Description;
import knbit.events.bc.common.domain.valueobjects.Name;
import knbit.events.bc.common.domain.valueobjects.Section;
import knbit.events.bc.common.domain.valueobjects.URL;
import knbit.events.bc.eventproposal.domain.enums.ProposalState;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import knbit.events.bc.eventproposal.domain.valueobjects.events.EventProposalEvents;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 05.05.15.
 */

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "newEventProposed")
public class EventProposedBuilder {

    private EventProposalId eventProposalId = EventProposalId.of("id");
    private Name name = Name.of("name");
    private Description description = Description.of("description");
    private EventType eventType = EventType.LECTURE;
    private ProposalState proposalState = ProposalState.PENDING;
    private URL imageUrl = URL.of("https://www.google.pl/");

    public EventProposalEvents.EventProposed build() {
        return new EventProposalEvents.EventProposed(
                eventProposalId, name, description,
                eventType, imageUrl, proposalState);
    }
}

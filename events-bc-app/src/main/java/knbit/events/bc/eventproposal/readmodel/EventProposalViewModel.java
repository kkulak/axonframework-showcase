package knbit.events.bc.eventproposal.readmodel;

import knbit.events.bc.eventproposal.domain.enums.EventType;
import knbit.events.bc.eventproposal.domain.enums.ProposalState;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by novy on 06.05.15.
 */

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = "id")
public class EventProposalViewModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String domainId;
    private String name;
    private String description;
    private EventType eventType;
    private ProposalState state;

    public EventProposalViewModel(String domainId, String name, String description, EventType eventType, ProposalState state) {
        this.domainId = domainId;
        this.name = name;
        this.description = description;
        this.eventType = eventType;
        this.state = state;
    }


}

package knbit.events.bc.domain.proposing.event.readmodel;

import knbit.events.bc.domain.proposing.event.EventType;
import knbit.events.bc.domain.proposing.event.valueobjects.ProposalState;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;
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

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    private EventType eventType;
    private ProposalState state;

    public EventProposalViewModel(String name, String description, EventType eventType, ProposalState state) {
        this.name = name;
        this.description = description;
        this.eventType = eventType;
        this.state = state;
    }


}

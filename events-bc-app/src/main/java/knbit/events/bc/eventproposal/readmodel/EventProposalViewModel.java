package knbit.events.bc.eventproposal.readmodel;

import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.readmodel.AbstractEntity;
import knbit.events.bc.eventproposal.domain.enums.ProposalState;
import lombok.*;

import javax.persistence.Entity;

/**
 * Created by novy on 06.05.15.
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class EventProposalViewModel extends AbstractEntity {

    private String domainId;
    private String name;
    private String description;
    private EventType eventType;
    private EventFrequency eventFrequency;
    private ProposalState state;

}

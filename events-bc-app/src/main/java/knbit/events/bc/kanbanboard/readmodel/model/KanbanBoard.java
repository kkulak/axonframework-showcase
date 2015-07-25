package knbit.events.bc.kanbanboard.readmodel.model;

import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.readmodel.AbstractEntity;
import knbit.events.bc.common.readmodel.EventStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class KanbanBoard extends AbstractEntity {

    private String eventDomainId;
    private String name;
    private EventType eventType;
    private EventFrequency eventFrequency;
    private EventStatus eventStatus;
    @ElementCollection(targetClass = EventStatus.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<EventStatus> reachableStatus;

}

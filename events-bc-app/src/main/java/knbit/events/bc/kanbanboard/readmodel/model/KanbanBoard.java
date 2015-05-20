package knbit.events.bc.kanbanboard.readmodel.model;

import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.readmodel.EventStatus;
import knbit.events.bc.common.domain.enums.EventType;
import knbit.events.bc.common.readmodel.AbstractEntity;
import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    @ElementCollection(targetClass = EventStatus.class)
    @Enumerated(EnumType.STRING)
    private List<EventStatus> reachableStatus;

}

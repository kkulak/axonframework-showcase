package knbit.events.bc.backlogevent.web;

import knbit.events.bc.backlogevent.web.forms.EventBacklogDTO;
import knbit.events.bc.backlogevent.web.forms.SectionDTO;
import knbit.events.bc.common.domain.valueobjects.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by novy on 29.11.15.
 */

@Component
public class EventDetailsDTOTransformer {

    public EventDetails eventDetailsFrom(EventBacklogDTO eventBacklogDTO) {
        return EventDetails.of(
                Name.of(eventBacklogDTO.getName()),
                Description.of(eventBacklogDTO.getDescription()),
                eventBacklogDTO.getEventType(),
                urlOrNull(eventBacklogDTO.getImageUrl()),
                sectionOrNull(eventBacklogDTO.getSection())
        );
    }

    private URL urlOrNull(Optional<String> value) {
        return value
                .map(URL::of)
                .orElse(null);
    }

    private Section sectionOrNull(Optional<SectionDTO> dto) {
        return dto
                .map(value -> Section.of(value.getId(), value.getName()))
                .orElse(null);
    }
}

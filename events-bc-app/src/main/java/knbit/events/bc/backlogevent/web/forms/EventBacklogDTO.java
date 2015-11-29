package knbit.events.bc.backlogevent.web.forms;

import knbit.events.bc.common.domain.enums.EventType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Data
@NoArgsConstructor
public class EventBacklogDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private EventType eventType;
    @NotNull
    private Optional<String> imageUrl = Optional.empty();
    @NotNull
    private Optional<SectionDTO> section = Optional.empty();

}

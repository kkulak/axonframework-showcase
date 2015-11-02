package knbit.events.bc.backlogevent.web.forms;

import knbit.events.bc.common.domain.enums.EventType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventBacklogDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private EventType eventType;
    @NotNull
    private Optional<String> imageUrl = Optional.empty();
    @NotNull @Valid
    private Optional<SectionDTO> section = Optional.empty();

}

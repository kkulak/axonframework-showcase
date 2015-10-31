package knbit.events.bc.backlogevent.web.forms;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SectionDTO {
    @NotBlank
    private String id;
    @NotBlank
    private String name;
}

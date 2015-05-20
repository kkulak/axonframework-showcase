package knbit.events.bc.eventproposal.web;

import knbit.events.bc.common.domain.enums.EventFrequency;
import knbit.events.bc.common.domain.enums.EventType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by novy on 05.05.15.
 */

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventProposalDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private EventType eventType;

    @NotNull
    private EventFrequency eventFrequency;

}

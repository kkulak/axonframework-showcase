package knbit.events.bc.eventready.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import knbit.events.bc.backlogevent.web.forms.EventBacklogDTO;
import lombok.*;
import lombok.experimental.Delegate;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by novy on 29.11.15.
 */

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventReadyDetailsDTO {

    @Delegate
    private EventBacklogDTO eventBacklogDTO = new EventBacklogDTO();
    @NotNull
    private DateTime start;
    @NotNull
    private DateTime end;
    @NotNull
    private Integer participantsLimit;
    @NotBlank
    private String location;
    @NotNull
    private Collection<LecturerDTO> lecturers;

    @Data
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    static class LecturerDTO {

        @NotBlank
        private String name;
        @NotNull
        private Optional<String> id = Optional.empty();
    }
}

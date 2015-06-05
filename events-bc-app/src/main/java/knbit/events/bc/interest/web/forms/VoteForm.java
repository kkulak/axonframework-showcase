package knbit.events.bc.interest.web.forms;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoteForm {

    @NotNull @Valid private VoteDTO type;
    @NotNull @Valid private AttendeeDTO attendee;
    @NotNull @Valid private Optional<List<SubmittedAnswerDTO>> answers = Optional.empty();

}

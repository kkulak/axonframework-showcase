package knbit.events.bc.interest.web.forms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SubmittedAnswerDTO {

    @NotNull @Valid private QuestionDataDTO question;
    @NotNull @Valid private List<String> answers;

}

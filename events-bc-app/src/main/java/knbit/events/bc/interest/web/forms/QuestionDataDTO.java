package knbit.events.bc.interest.web.forms;

import knbit.events.bc.interest.domain.enums.AnswerType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class QuestionDataDTO {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private AnswerType type;
    @NotNull
    private List<String> answers;

}

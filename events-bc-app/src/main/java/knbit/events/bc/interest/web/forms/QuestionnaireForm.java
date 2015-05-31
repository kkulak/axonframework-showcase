package knbit.events.bc.interest.web.forms;

import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionData;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionnaireForm {

    @NotNull private List<QuestionData> questions;

}

package knbit.events.bc.interest.web.forms;

import knbit.events.bc.interest.domain.enums.AnswerType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SurveyForm {

    private Optional<DateTime> endOfSurveying = Optional.empty();
    private Optional<Integer> minimalInterestThreshold = Optional.empty();
    private List<QuestionDataDTO> questions = new LinkedList<>();

    @Data
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class QuestionDataDTO {

        @NotNull
        private String title;
        @NotNull
        private String description;
        @NotNull
        private AnswerType type;
        @NotNull
        private List<String> answers;
    }

}

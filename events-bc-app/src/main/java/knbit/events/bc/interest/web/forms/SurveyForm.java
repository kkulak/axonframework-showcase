package knbit.events.bc.interest.web.forms;

import knbit.events.bc.interest.domain.enums.AnswerType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SurveyForm {

    private Optional<DateTime> endOfSurveying;
    private Optional<Integer> minimalInterestThreshold;
    @NotNull
    private List<QuestionDataDTO> questions;

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

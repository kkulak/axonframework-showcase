package knbit.events.bc.interest.web.forms;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SurveyForm {

    private Optional<DateTime> endOfSurveying = Optional.empty();
    private Optional<Integer> minimalInterestThreshold = Optional.empty();
    private List<QuestionDataDTO> questions = new LinkedList<>();

}

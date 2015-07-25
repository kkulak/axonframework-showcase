package knbit.events.bc.interest.domain.valueobjects.question.answer;

import knbit.events.bc.interest.domain.valueobjects.question.QuestionData;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 26.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class AnsweredQuestion {

    private final QuestionData questionData;
    private final List<DomainAnswer> answers;

}

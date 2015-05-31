package knbit.events.bc.interest.domain.valueobjects.submittedanswer;

import knbit.events.bc.interest.domain.valueobjects.question.QuestionData;
import knbit.events.bc.interest.domain.valueobjects.question.answer.DomainAnswer;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 25.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class SubmittedAnswer {

    private final QuestionData questionData;
    private final List<DomainAnswer> answers;

}

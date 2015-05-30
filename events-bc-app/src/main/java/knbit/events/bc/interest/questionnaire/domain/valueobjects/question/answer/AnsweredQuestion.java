package knbit.events.bc.interest.questionnaire.domain.valueobjects.question.answer;

import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.Question;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 26.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class AnsweredQuestion {

    private final Question question;
    private final List<DomainAnswer> answers;

}

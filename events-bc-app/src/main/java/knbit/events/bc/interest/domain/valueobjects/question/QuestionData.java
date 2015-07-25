package knbit.events.bc.interest.domain.valueobjects.question;

import knbit.events.bc.interest.domain.enums.AnswerType;
import knbit.events.bc.interest.domain.valueobjects.question.answer.DomainAnswer;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 26.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class QuestionData {

    private final QuestionTitle title;
    private final QuestionDescription description;
    private final AnswerType answerType;
    private final List<DomainAnswer> possibleAnswers;

}

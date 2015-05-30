package knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import knbit.events.bc.interest.questionnaire.domain.entities.AnswerChecker;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.AnsweredQuestion;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.DomainAnswer;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 25.05.15.
 */

@Accessors(fluent = true)
@Value
@EqualsAndHashCode(callSuper = false)
public class SingleChoiceAnswer extends SubmittedAnswer {

    private final String value;

    public SingleChoiceAnswer(QuestionId questionId, String value) {
        super(questionId);

        Preconditions.checkArgument(
                !Strings.isNullOrEmpty(value)
        );

        this.value = value;
    }

    @Override
    public List<DomainAnswer> unwrap() {
        return ImmutableList.of(
                DomainAnswer.of(value)
        );
    }

    @Override
    public AnsweredQuestion allowCheckingBy(AnswerChecker checker) {
        return checker.check(this);
    }
}

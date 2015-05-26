package knbit.events.bc.questionnaire.voting.domain.valueobjects.submittedanswer;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import knbit.events.bc.questionnaire.voting.domain.entities.AnswerChecker;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.AnsweredQuestion;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.DomainAnswer;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.QuestionnaireId;
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
public class TextAnswer extends SubmittedAnswer {

    private final String value;

    public TextAnswer(QuestionnaireId questionnaireId, String value) {
        super(questionnaireId);

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

package knbit.events.bc.questionnaire.voting.domain.valueobjects.submittedanswer;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import knbit.events.bc.questionnaire.voting.domain.entities.AnswerChecker;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.AnsweredQuestion;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.DomainAnswer;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.QuestionnaireId;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by novy on 25.05.15.
 */

@Accessors(fluent = true)
@Value
@EqualsAndHashCode(callSuper = false)
public class MultipleChoiceAnswer extends SubmittedAnswer {

    private final List<String> submittedAnswers;

    public MultipleChoiceAnswer(QuestionnaireId questionnaireId, List<String> submittedAnswers) {
        super(questionnaireId);

        Preconditions.checkArgument(!submittedAnswers.isEmpty());
        Preconditions.checkArgument(hasNonEmptyAnswers(submittedAnswers));

        this.submittedAnswers = submittedAnswers;
    }

    private boolean hasNonEmptyAnswers(Collection<String> answers) {
        return answers
                .stream()
                .noneMatch(Strings::isNullOrEmpty);
    }

    @Override
    public List<DomainAnswer> unwrap() {
        return submittedAnswers
                .stream()
                .map(DomainAnswer::of)
                .collect(Collectors.toList());

    }

    @Override
    public AnsweredQuestion allowCheckingBy(AnswerChecker checker) {
        return checker.check(this);
    }
}

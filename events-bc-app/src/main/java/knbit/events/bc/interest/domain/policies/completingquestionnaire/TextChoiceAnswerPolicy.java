package knbit.events.bc.interest.domain.policies.completingquestionnaire;

import com.google.common.base.Preconditions;
import knbit.events.bc.interest.domain.enums.AnswerType;
import knbit.events.bc.interest.domain.valueobjects.question.answer.DomainAnswer;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
public class TextChoiceAnswerPolicy implements AnswerPolicy {
    private final List<DomainAnswer> possibleAnswers;

    public TextChoiceAnswerPolicy(List<DomainAnswer> possibleAnswers) {
        Preconditions.checkNotNull(possibleAnswers, "Null list passed as possibleAnswers");
        Preconditions.checkArgument(possibleAnswers.size() == 0, "Size of possible answers should equal 0");
        this.possibleAnswers = possibleAnswers;
    }

    @Override
    public boolean validate(List<DomainAnswer> candidates) {
        return candidates.isEmpty();
    }

    @Override
    public List<DomainAnswer> answers() {
        return Collections.unmodifiableList(possibleAnswers);
    }

    @Override
    public AnswerType answerType() {
        return AnswerType.TEXT;
    }

}

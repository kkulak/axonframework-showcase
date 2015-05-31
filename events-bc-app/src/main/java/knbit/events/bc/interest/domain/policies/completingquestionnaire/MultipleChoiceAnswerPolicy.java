package knbit.events.bc.interest.domain.policies.completingquestionnaire;

import com.google.common.base.Preconditions;
import knbit.events.bc.interest.domain.enums.AnswerType;
import knbit.events.bc.interest.domain.valueobjects.question.answer.DomainAnswer;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
public class MultipleChoiceAnswerPolicy implements AnswerPolicy {
    private final List<DomainAnswer> possibleAnswers;

    public MultipleChoiceAnswerPolicy(List<DomainAnswer> possibleAnswers) {
        Preconditions.checkNotNull(possibleAnswers, "Null list passed as possibleAnswers");
        Preconditions.checkArgument(possibleAnswers.size() > 0, "Size of possible answers should be at least 1");
        this.possibleAnswers = possibleAnswers;
    }

    @Override
    public boolean validate(List<DomainAnswer> candidates) {
        return hasAtLeastOneAnswer(candidates) && isInPossibleAnswers(candidates);
    }

    @Override
    public List<DomainAnswer> answers() {
        return Collections.unmodifiableList(possibleAnswers);
    }

    @Override
    public AnswerType answerType() {
        return AnswerType.MULTIPLE_CHOICE;
    }

    private boolean hasAtLeastOneAnswer(List<DomainAnswer> candidates) {
        return candidates.size() > 0;
    }

    private boolean isInPossibleAnswers(List<DomainAnswer> candidates) {
        return possibleAnswers.containsAll(candidates);
    }

}

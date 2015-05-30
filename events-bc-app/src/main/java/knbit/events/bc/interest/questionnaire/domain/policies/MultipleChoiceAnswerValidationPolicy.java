package knbit.events.bc.interest.questionnaire.domain.policies;

import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.answer.DomainAnswer;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
public class MultipleChoiceAnswerValidationPolicy implements AnswerValidationPolicy {

    @Override
    public boolean validate(List<DomainAnswer> possibleAnswers, List<DomainAnswer> candidates) {
        return hasAtLeastOneAnswer(candidates) && isInPossibleAnswers(possibleAnswers, candidates);
    }

    private boolean hasAtLeastOneAnswer(List<DomainAnswer> candidates) {
        return candidates.size() > 0;
    }

    private boolean isInPossibleAnswers(List<DomainAnswer> possibleAnswers, List<DomainAnswer> candidates) {
        return possibleAnswers.containsAll(candidates);
    }

}

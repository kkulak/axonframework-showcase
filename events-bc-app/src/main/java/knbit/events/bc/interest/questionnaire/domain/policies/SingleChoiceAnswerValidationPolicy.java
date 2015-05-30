package knbit.events.bc.interest.questionnaire.domain.policies;

import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.answer.DomainAnswer;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
public class SingleChoiceAnswerValidationPolicy implements AnswerValidationPolicy {

    @Override
    public boolean validate(List<DomainAnswer> possibleAnswers, List<DomainAnswer> candidates) {
        return hasExactlyOneAnswer(candidates) && isInPossibleAnswers(possibleAnswers, candidates);
    }

    private boolean hasExactlyOneAnswer(List<DomainAnswer> candidates) {
        return candidates.size() == 1;
    }

    private boolean isInPossibleAnswers(List<DomainAnswer> possibleAnswers, List<DomainAnswer> candidates) {
        return possibleAnswers.containsAll(candidates);
    }

}

package knbit.events.bc.interest.questionnaire.domain.policies;

import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.answer.DomainAnswer;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
public class TextChoiceAnswerValidationPolicy implements AnswerValidationPolicy {

    @Override
    public boolean validate(List<DomainAnswer> possibleAnswers, List<DomainAnswer> candidates) {
        return candidates.isEmpty();
    }

}

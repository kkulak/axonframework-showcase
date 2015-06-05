package knbit.events.bc.interest.domain.policies.completingquestionnaire;

import knbit.events.bc.interest.domain.enums.AnswerType;
import knbit.events.bc.interest.domain.valueobjects.question.answer.DomainAnswer;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
public class TextChoiceAnswerPolicy implements AnswerPolicy {

    @Override
    public boolean validate(List<DomainAnswer> candidates) {
        return candidates.isEmpty();
    }

    @Override
    public List<DomainAnswer> answers() {
        return Collections.emptyList();
    }

    @Override
    public AnswerType answerType() {
        return AnswerType.TEXT;
    }

}

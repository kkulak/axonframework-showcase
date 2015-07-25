package knbit.events.bc.interest.domain.policies.completingquestionnaire;

import knbit.events.bc.interest.domain.enums.AnswerType;
import knbit.events.bc.interest.domain.valueobjects.question.answer.DomainAnswer;

import java.util.List;

public interface AnswerPolicy {

    boolean validate(List<DomainAnswer> candidates);

    List<DomainAnswer> answers();

    AnswerType answerType();

}

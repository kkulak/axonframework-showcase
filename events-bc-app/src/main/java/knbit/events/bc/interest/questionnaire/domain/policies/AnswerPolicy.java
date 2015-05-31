package knbit.events.bc.interest.questionnaire.domain.policies;

import knbit.events.bc.interest.questionnaire.domain.enums.AnswerType;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.answer.DomainAnswer;

import java.util.List;

public interface AnswerPolicy {

    boolean validate(List<DomainAnswer> candidates);
    List<DomainAnswer> answers();
    AnswerType answerType();

}

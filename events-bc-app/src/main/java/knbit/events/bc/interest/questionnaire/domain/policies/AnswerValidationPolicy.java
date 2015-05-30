package knbit.events.bc.interest.questionnaire.domain.policies;

import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.answer.DomainAnswer;

import java.util.List;

public interface AnswerValidationPolicy {

    boolean validate(List<DomainAnswer> possibleAnswers, List<DomainAnswer> candidates);

}

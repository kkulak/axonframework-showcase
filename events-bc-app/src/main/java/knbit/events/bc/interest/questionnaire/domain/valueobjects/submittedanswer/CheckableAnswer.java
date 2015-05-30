package knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer;

import knbit.events.bc.interest.questionnaire.domain.entities.AnswerChecker;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.AnsweredQuestion;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.DomainAnswer;

import java.util.List;

/**
 * Created by novy on 25.05.15.
 */
public interface CheckableAnswer {

    QuestionId questionId();

    List<DomainAnswer> unwrap();

    AnsweredQuestion allowCheckingBy(AnswerChecker checker);
}

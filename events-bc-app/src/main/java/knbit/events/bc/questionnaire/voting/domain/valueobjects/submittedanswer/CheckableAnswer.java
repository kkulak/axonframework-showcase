package knbit.events.bc.questionnaire.voting.domain.valueobjects.submittedanswer;

import knbit.events.bc.questionnaire.voting.domain.entities.AnswerChecker;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.AnsweredQuestion;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.DomainAnswer;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.QuestionnaireId;

import java.util.List;

/**
 * Created by novy on 25.05.15.
 */
public interface CheckableAnswer {

    QuestionnaireId questionnaireId();

    List<DomainAnswer> unwrap();

    AnsweredQuestion allowCheckingBy(AnswerChecker checker);
}

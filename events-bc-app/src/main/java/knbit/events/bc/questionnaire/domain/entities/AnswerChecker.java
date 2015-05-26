package knbit.events.bc.questionnaire.domain.entities;

import knbit.events.bc.questionnaire.domain.valueobjects.question.AnsweredQuestion;
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.MultipleChoiceAnswer;
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.SingleChoiceAnswer;
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.TextAnswer;

/**
 * Created by novy on 25.05.15.
 */
public interface AnswerChecker {

    AnsweredQuestion check(SingleChoiceAnswer answer);

    AnsweredQuestion check(MultipleChoiceAnswer answer);

    AnsweredQuestion check(TextAnswer answer);
}

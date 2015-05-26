package knbit.events.bc.questionnaire.voting.domain.entities;

import knbit.events.bc.questionnaire.voting.domain.valueobjects.AnsweredQuestion;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.QuestionId;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.submittedanswer.SingleChoiceAnswer;

/**
 * Created by novy on 26.05.15.
 */
public class SingleChoiceQuestion extends Question {

    public SingleChoiceQuestion(QuestionId questionId) {
        super(questionId);
    }

    @Override
    public AnsweredQuestion check(SingleChoiceAnswer answer) {
        return super.check(answer);
    }
}

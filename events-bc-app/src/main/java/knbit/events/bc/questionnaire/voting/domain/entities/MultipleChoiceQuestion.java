package knbit.events.bc.questionnaire.voting.domain.entities;

import knbit.events.bc.questionnaire.voting.domain.valueobjects.AnsweredQuestion;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.QuestionId;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.submittedanswer.MultipleChoiceAnswer;

/**
 * Created by novy on 25.05.15.
 */
public class MultipleChoiceQuestion extends Question {

    public MultipleChoiceQuestion(QuestionId questionId) {
        super(questionId);
    }

    @Override
    public AnsweredQuestion check(MultipleChoiceAnswer answer) {
        return super.check(answer);
    }
}

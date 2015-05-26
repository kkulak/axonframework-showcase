package knbit.events.bc.questionnaire.voting.domain.entities;

import knbit.events.bc.questionnaire.voting.domain.valueobjects.AnsweredQuestion;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.QuestionId;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.submittedanswer.TextAnswer;

/**
 * Created by novy on 26.05.15.
 */
public class TextQuestion extends Question {

    public TextQuestion(QuestionId questionId) {
        super(questionId);
    }

    @Override
    public AnsweredQuestion check(TextAnswer answer) {
        return super.check(answer);
    }
}

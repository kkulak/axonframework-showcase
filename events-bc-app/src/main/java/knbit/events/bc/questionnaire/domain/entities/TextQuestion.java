package knbit.events.bc.questionnaire.domain.entities;

import knbit.events.bc.questionnaire.domain.valueobjects.AnsweredQuestion;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionId;
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.CheckableAnswer;
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.TextAnswer;

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

    @Override
    protected Class<? extends CheckableAnswer> expectedAnswerClass() {
        return TextAnswer.class;
    }
}

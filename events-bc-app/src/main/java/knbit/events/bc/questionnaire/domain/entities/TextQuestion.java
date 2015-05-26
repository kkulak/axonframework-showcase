package knbit.events.bc.questionnaire.domain.entities;

import knbit.events.bc.questionnaire.domain.valueobjects.question.AnsweredQuestion;
import knbit.events.bc.questionnaire.domain.valueobjects.question.QuestionDescription;
import knbit.events.bc.questionnaire.domain.valueobjects.question.QuestionTitle;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionId;
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.CheckableAnswer;
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.TextAnswer;

/**
 * Created by novy on 26.05.15.
 */
public class TextQuestion extends Question {

    public TextQuestion(QuestionId questionId, QuestionTitle title, QuestionDescription description) {
        super(questionId, title, description);
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

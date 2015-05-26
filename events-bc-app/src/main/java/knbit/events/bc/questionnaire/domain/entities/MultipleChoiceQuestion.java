package knbit.events.bc.questionnaire.domain.entities;

import knbit.events.bc.questionnaire.domain.valueobjects.AnsweredQuestion;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionId;
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.CheckableAnswer;
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.MultipleChoiceAnswer;

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

    @Override
    protected Class<? extends CheckableAnswer> expectedAnswerClass() {
        return MultipleChoiceAnswer.class;
    }
}

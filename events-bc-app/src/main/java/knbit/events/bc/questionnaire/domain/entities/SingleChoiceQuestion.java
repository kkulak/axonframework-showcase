package knbit.events.bc.questionnaire.domain.entities;

import knbit.events.bc.questionnaire.domain.valueobjects.question.AnsweredQuestion;
import knbit.events.bc.questionnaire.domain.valueobjects.question.DomainAnswer;
import knbit.events.bc.questionnaire.domain.valueobjects.question.QuestionDescription;
import knbit.events.bc.questionnaire.domain.valueobjects.question.QuestionTitle;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionId;
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.CheckableAnswer;
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.SingleChoiceAnswer;

import java.util.List;

/**
 * Created by novy on 26.05.15.
 */
public class SingleChoiceQuestion extends Question {

    private final List<DomainAnswer> possibleAnswers;

    public SingleChoiceQuestion(QuestionId questionId, QuestionTitle title, QuestionDescription description, List<DomainAnswer> possibleAnswers) {
        super(questionId, title, description);
        this.possibleAnswers = possibleAnswers;
    }

    @Override
    public AnsweredQuestion check(SingleChoiceAnswer answer) {
        return super.check(answer);
    }

    @Override
    protected Class<? extends CheckableAnswer> expectedAnswerClass() {
        return SingleChoiceAnswer.class;
    }
}

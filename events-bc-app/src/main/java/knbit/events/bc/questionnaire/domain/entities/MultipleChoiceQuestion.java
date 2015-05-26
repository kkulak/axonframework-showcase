package knbit.events.bc.questionnaire.domain.entities;

import knbit.events.bc.questionnaire.domain.valueobjects.question.AnsweredQuestion;
import knbit.events.bc.questionnaire.domain.valueobjects.question.DomainAnswer;
import knbit.events.bc.questionnaire.domain.valueobjects.question.QuestionDescription;
import knbit.events.bc.questionnaire.domain.valueobjects.question.QuestionTitle;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionId;
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.CheckableAnswer;
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.MultipleChoiceAnswer;

import java.util.List;

/**
 * Created by novy on 25.05.15.
 */
public class MultipleChoiceQuestion extends Question {

    private final List<DomainAnswer> possibleAnswers;

    public MultipleChoiceQuestion(QuestionId questionId, QuestionTitle title, QuestionDescription description, List<DomainAnswer> possibleAnswers) {
        super(questionId, title, description);
        this.possibleAnswers = possibleAnswers;
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

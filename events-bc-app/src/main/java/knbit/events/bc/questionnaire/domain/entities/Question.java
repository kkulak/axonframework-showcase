package knbit.events.bc.questionnaire.domain.entities;

import knbit.events.bc.common.domain.IdentifiedDomainEntity;
import knbit.events.bc.questionnaire.domain.exceptions.IncorrectAnswerTypeException;
import knbit.events.bc.questionnaire.domain.valueobjects.question.AnsweredQuestion;
import knbit.events.bc.questionnaire.domain.valueobjects.question.QuestionDescription;
import knbit.events.bc.questionnaire.domain.valueobjects.question.QuestionTitle;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionId;
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.CheckableAnswer;
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.MultipleChoiceAnswer;
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.SingleChoiceAnswer;
import knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer.TextAnswer;

/**
 * Created by novy on 26.05.15.
 */
public abstract class Question extends IdentifiedDomainEntity<QuestionId> implements AnswerChecker {

    protected final QuestionTitle title;
    protected final QuestionDescription description;

    public Question(QuestionId questionId, QuestionTitle title, QuestionDescription description) {
        super(questionId);
        this.title = title;
        this.description = description;
    }

    @Override
    public AnsweredQuestion check(SingleChoiceAnswer answer) {
        throw new IncorrectAnswerTypeException(
                id, expectedAnswerClass(), SingleChoiceAnswer.class
        );
    }

    @Override
    public AnsweredQuestion check(MultipleChoiceAnswer answer) {
        throw new IncorrectAnswerTypeException(
                id, expectedAnswerClass(), MultipleChoiceAnswer.class
        );
    }

    @Override
    public AnsweredQuestion check(TextAnswer answer) {
        throw new IncorrectAnswerTypeException(
                id, expectedAnswerClass(), TextAnswer.class
        );
    }

    protected abstract Class<? extends CheckableAnswer> expectedAnswerClass();
}

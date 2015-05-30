package knbit.events.bc.interest.questionnaire.domain.entities;

import knbit.events.bc.common.domain.IdentifiedDomainEntity;
import knbit.events.bc.interest.questionnaire.domain.exceptions.IncorrectAnswerTypeException;
import knbit.events.bc.interest.questionnaire.domain.exceptions.QuestionIdDoesNotMatchException;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.AnsweredQuestion;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionDescription;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionTitle;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.CheckableAnswer;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.MultipleChoiceAnswer;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.SingleChoiceAnswer;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.TextAnswer;

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

    protected void checkForIdEquality(CheckableAnswer answer) {
        if (!id.equals(answer.questionId())) {
            throw new QuestionIdDoesNotMatchException(id, answer.questionId());
        }
    }

    protected abstract Class<? extends CheckableAnswer> expectedAnswerClass();
}

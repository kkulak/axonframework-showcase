package knbit.events.bc.questionnaire.voting.domain.entities;

import knbit.events.bc.common.domain.IdentifiedDomainEntity;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.AnsweredQuestion;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.QuestionId;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.submittedanswer.MultipleChoiceAnswer;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.submittedanswer.SingleChoiceAnswer;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.submittedanswer.TextAnswer;

/**
 * Created by novy on 26.05.15.
 */
public abstract class Question extends IdentifiedDomainEntity<QuestionId> implements AnswerChecker {

    public Question(QuestionId questionId) {
        super(questionId);
    }

    @Override
    public AnsweredQuestion check(SingleChoiceAnswer answer) {
        return null;
    }

    @Override
    public AnsweredQuestion check(MultipleChoiceAnswer answer) {
        return null;
    }

    @Override
    public AnsweredQuestion check(TextAnswer answer) {
        return null;
    }
}

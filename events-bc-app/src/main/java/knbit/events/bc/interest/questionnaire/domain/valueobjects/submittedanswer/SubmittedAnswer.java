package knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer;

import com.google.common.base.Preconditions;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionId;

/**
 * Created by novy on 25.05.15.
 */
public abstract class SubmittedAnswer implements CheckableAnswer {

    private final QuestionId questionId;

    public SubmittedAnswer(QuestionId questionId) {
        this.questionId = Preconditions.checkNotNull(questionId);
    }

    @Override
    public QuestionId questionId() {
        return questionId;
    }
}

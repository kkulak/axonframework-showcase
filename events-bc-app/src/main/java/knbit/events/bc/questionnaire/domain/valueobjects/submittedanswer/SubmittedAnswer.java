package knbit.events.bc.questionnaire.domain.valueobjects.submittedanswer;

import com.google.common.base.Preconditions;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionId;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionnaireId;

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

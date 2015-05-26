package knbit.events.bc.questionnaire.voting.domain.valueobjects.submittedanswer;

import knbit.events.bc.questionnaire.voting.domain.valueobjects.QuestionnaireId;

/**
 * Created by novy on 25.05.15.
 */
public abstract class SubmittedAnswer implements CheckableAnswer {

    private final QuestionnaireId questionnaireId;

    public SubmittedAnswer(QuestionnaireId questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    @Override
    public QuestionnaireId questionnaireId() {
        return questionnaireId;
    }
}

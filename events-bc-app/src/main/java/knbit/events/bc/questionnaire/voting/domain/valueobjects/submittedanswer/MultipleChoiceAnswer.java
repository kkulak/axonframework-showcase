package knbit.events.bc.questionnaire.voting.domain.valueobjects.submittedanswer;

import knbit.events.bc.questionnaire.voting.domain.entities.AnswerChecker;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.AnsweredQuestion;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.DomainAnswer;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.QuestionnaireId;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * Created by novy on 25.05.15.
 */

@Accessors(fluent = true)
@Value
public class MultipleChoiceAnswer extends SubmittedAnswer {

    public MultipleChoiceAnswer(QuestionnaireId questionnaireId) {
        super(questionnaireId);
    }

    @Override
    public Collection<DomainAnswer> unwrap() {
        return null;
    }

    @Override
    public AnsweredQuestion allowCheckingBy(AnswerChecker checker) {
        return null;
    }
}

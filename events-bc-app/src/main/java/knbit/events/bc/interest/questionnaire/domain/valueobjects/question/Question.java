package knbit.events.bc.interest.questionnaire.domain.valueobjects.question;

import com.google.common.collect.Lists;
import knbit.events.bc.interest.questionnaire.domain.exceptions.IncorrectChoiceException;
import knbit.events.bc.interest.questionnaire.domain.policies.AnswerValidationPolicy;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.answer.AnsweredQuestion;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.answer.DomainAnswer;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionDescription;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionTitle;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.SubmittedAnswer;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by novy on 26.05.15.
 */
@EqualsAndHashCode
public class Question {

    private final QuestionTitle title;
    private final QuestionDescription description;
    private final AnswerValidationPolicy validationPolicy;
    private final List<DomainAnswer> possibleAnswers = Lists.newLinkedList();

    public Question(QuestionTitle title,
                    QuestionDescription description,
                    AnswerValidationPolicy validationPolicy,
                    List<DomainAnswer> possibleAnswers) {
        this.title = title;
        this.description = description;
        this.validationPolicy = validationPolicy;
        this.possibleAnswers.addAll(possibleAnswers);
    }

    public AnsweredQuestion answer(SubmittedAnswer answer) {
        checkForChoiceCorrectness(answer);

        return AnsweredQuestion.of(
                this, answer.answers()
        );
    }

    private void checkForChoiceCorrectness(SubmittedAnswer answer) {
        if(!validationPolicy.validate(possibleAnswers, answer.answers())) {
            throw new IncorrectChoiceException(this);
        }
    }

}

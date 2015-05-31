package knbit.events.bc.interest.questionnaire.domain.valueobjects.question;

import com.google.common.base.Preconditions;
import knbit.events.bc.interest.questionnaire.domain.exceptions.IncorrectChoiceException;
import knbit.events.bc.interest.questionnaire.domain.policies.AnswerPolicy;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.answer.AnsweredQuestion;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.SubmittedAnswer;
import lombok.EqualsAndHashCode;

/**
 * Created by novy on 26.05.15.
 */
@EqualsAndHashCode
public class Question {

    private final QuestionTitle title;
    private final QuestionDescription description;
    private final AnswerPolicy answerPolicy;

    private Question(QuestionTitle title,
                    QuestionDescription description,
                    AnswerPolicy answerPolicy) {
        this.title = Preconditions.checkNotNull(title);
        this.description = Preconditions.checkNotNull(description);
        this.answerPolicy = Preconditions.checkNotNull(answerPolicy);
    }

    public static Question of(QuestionTitle title,
                              QuestionDescription description,
                              AnswerPolicy answerPolicy) {
        return new Question(title, description, answerPolicy);
    }

    public AnsweredQuestion answer(SubmittedAnswer answer) {
        checkForChoiceCorrectness(answer);

        return AnsweredQuestion.of(
                QuestionData.of(
                        title, description, answerPolicy.answerType(), answerPolicy.answers()
                ),
                answer.answers()
        );
    }

    private void checkForChoiceCorrectness(SubmittedAnswer answer) {
        if(!answerPolicy.validate(answer.answers())) {
            throw new IncorrectChoiceException(this);
        }
    }

}

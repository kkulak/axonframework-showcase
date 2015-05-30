package knbit.events.bc.interest.questionnaire.domain.entities;

import knbit.events.bc.interest.questionnaire.domain.enums.QuestionType;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.AnsweredQuestion;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionDescription;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionTitle;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.CheckableAnswer;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.TextAnswer;

import java.util.Collections;

/**
 * Created by novy on 26.05.15.
 */
public class TextQuestion extends Question {

    public TextQuestion(QuestionId questionId, QuestionTitle title, QuestionDescription description) {
        super(questionId, title, description);
    }

    @Override
    public AnsweredQuestion check(TextAnswer answer) {
        checkForIdEquality(answer);

        return AnsweredQuestion.of(
                id, title, description, QuestionType.TEXT, Collections.emptyList(), answer.unwrap()
        );
    }

    @Override
    protected Class<? extends CheckableAnswer> expectedAnswerClass() {
        return TextAnswer.class;
    }
}

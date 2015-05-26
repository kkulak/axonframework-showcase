package knbit.events.bc.questionnaire.domain.valueobjects.question;

import knbit.events.bc.questionnaire.domain.enums.QuestionType;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionId;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 26.05.15.
 */

@Accessors(fluent = true)
@Value
public class AnsweredQuestion {

    private final IdentifiedQuestionData questionData;
    private final List<DomainAnswer> answers;

    public static AnsweredQuestion of(IdentifiedQuestionData questionData, List<DomainAnswer> answers) {
        return new AnsweredQuestion(questionData, answers);
    }

    private AnsweredQuestion(IdentifiedQuestionData questionData, List<DomainAnswer> answers) {
        this.questionData = questionData;
        this.answers = answers;
    }

    public static AnsweredQuestion of(QuestionId questionId, QuestionTitle title,
                                      QuestionDescription description, QuestionType questionType,
                                      List<DomainAnswer> possibleAnswers, List<DomainAnswer> answers) {
        return new AnsweredQuestion(questionId, title, description, questionType, possibleAnswers, answers);
    }

    private AnsweredQuestion(QuestionId questionId, QuestionTitle title,
                             QuestionDescription description, QuestionType questionType,
                             List<DomainAnswer> possibleAnswers, List<DomainAnswer> answers) {

        this.questionData = IdentifiedQuestionData.of(questionId, title, description, questionType, possibleAnswers);
        this.answers = answers;

    }

    public QuestionId questionId() {
        return questionData.questionId();
    }

    public QuestionTitle title() {
        return questionData.title();
    }

    public QuestionDescription description() {
        return questionData.description();
    }

    public QuestionType questionType() {
        return questionData.questionType();
    }

    public List<DomainAnswer> possibleAnswers() {
        return questionData.possibleAnswers();
    }


}

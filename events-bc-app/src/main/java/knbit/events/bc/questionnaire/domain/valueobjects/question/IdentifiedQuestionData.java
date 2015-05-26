package knbit.events.bc.questionnaire.domain.valueobjects.question;

import knbit.events.bc.questionnaire.domain.enums.QuestionType;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 26.05.15.
 */

@Accessors(fluent = true)
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode
@ToString
public class IdentifiedQuestionData {

    @Getter
    private final QuestionId questionId;
    private final QuestionData questionData;

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

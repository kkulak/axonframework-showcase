package knbit.events.bc.questionnaire.domain.valueobjects.question;

import com.google.common.base.Preconditions;
import knbit.events.bc.questionnaire.domain.enums.QuestionType;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by novy on 26.05.15.
 */

@Accessors(fluent = true)
@Value
public class QuestionData {

    private final QuestionTitle title;
    private final QuestionDescription description;
    private final QuestionType questionType;
    private final List<DomainAnswer> possibleAnswers;

    public static QuestionData of(String title, String description, QuestionType questionType, List<String> possibleAnswers) {
        return new QuestionData(title, description, questionType, possibleAnswers);
    }

    private QuestionData(String title, String description, QuestionType questionType, List<String> possibleAnswers) {
        Preconditions.checkNotNull(questionType);

        checkPossibleAnswersAccordingToQuestionType(possibleAnswers, questionType);

        this.title = QuestionTitle.of(title);
        this.description = QuestionDescription.of(description);
        this.questionType = questionType;
        this.possibleAnswers = toDomainAnswers(possibleAnswers);
    }

    private void checkPossibleAnswersAccordingToQuestionType(List<String> possibleAnswers, QuestionType questionType) {
        if (questionType == QuestionType.TEXT) {
            Preconditions.checkArgument(
                    possibleAnswers.isEmpty()
            );
        } else {
            Preconditions.checkArgument(
                    !possibleAnswers.isEmpty()
            );
        }
    }

    private List<DomainAnswer> toDomainAnswers(List<String> possibleAnswers) {
        return possibleAnswers
                .stream()
                .map(DomainAnswer::of)
                .collect(Collectors.toList());
    }
}

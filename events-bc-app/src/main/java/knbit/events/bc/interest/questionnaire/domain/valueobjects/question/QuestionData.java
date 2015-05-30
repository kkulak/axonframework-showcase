package knbit.events.bc.interest.questionnaire.domain.valueobjects.question;

import com.google.common.base.Preconditions;
import knbit.events.bc.interest.questionnaire.domain.enums.QuestionType;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.answer.DomainAnswer;
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

    public static QuestionData of(QuestionTitle title, QuestionDescription description, QuestionType questionType, List<DomainAnswer> possibleAnswers) {
        return new QuestionData(title, description, questionType, possibleAnswers);
    }

    private QuestionData(QuestionTitle title, QuestionDescription description, QuestionType questionType, List<DomainAnswer> possibleAnswers) {
        Preconditions.checkNotNull(questionType);

        checkPossibleAnswersAccordingToQuestionType(possibleAnswers, questionType);

        this.title = title;
        this.description = description;
        this.questionType = questionType;
        this.possibleAnswers = possibleAnswers;
    }

    private QuestionData(String title, String description, QuestionType questionType, List<String> possibleAnswers) {
        this(
                QuestionTitle.of(title), QuestionDescription.of(description),
                questionType, toDomainAnswers(possibleAnswers)
        );
    }

    private void checkPossibleAnswersAccordingToQuestionType(List<DomainAnswer> possibleAnswers, QuestionType questionType) {
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

    private static List<DomainAnswer> toDomainAnswers(List<String> possibleAnswers) {
        return possibleAnswers
                .stream()
                .map(DomainAnswer::of)
                .collect(Collectors.toList());
    }
}

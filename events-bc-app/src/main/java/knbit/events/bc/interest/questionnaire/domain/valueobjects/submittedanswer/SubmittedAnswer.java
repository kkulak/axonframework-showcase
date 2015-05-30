package knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.Question;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.answer.DomainAnswer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by novy on 25.05.15.
 */
public class SubmittedAnswer {
    private final Question question;
    private final List<String> rawAnswers;

    private SubmittedAnswer(Question question, List<String> rawAnswers) {
        this.question = question;
        this.rawAnswers = rawAnswers;
    }

    public static SubmittedAnswer of(Question question, List<String> rawAnswers) {
        Preconditions.checkNotNull(question);
        check(rawAnswers);
        return new SubmittedAnswer(question, rawAnswers);
    }

    private static void check(List<String> rawAnswers) {
        Preconditions.checkArgument(rawAnswers != null, "Null reference passed as answers");
        Preconditions.checkArgument(!containsEmptyAnswers(rawAnswers), "Answers contains empty text");
    }

    private static boolean containsEmptyAnswers(List<String> rawAnswers) {
        return rawAnswers
                .stream()
                .anyMatch(Strings::isNullOrEmpty);
    }

    public Question question() {
        return question;
    }

    public List<DomainAnswer> answers() {
        return rawAnswers
                .stream()
                .map(DomainAnswer::of)
                .collect(Collectors.toList());
    }

}

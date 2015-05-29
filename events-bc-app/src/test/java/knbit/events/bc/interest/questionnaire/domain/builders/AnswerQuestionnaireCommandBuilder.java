package knbit.events.bc.interest.questionnaire.domain.builders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.commands.AnswerQuestionnaireCommand;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.CheckableAnswer;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.MultipleChoiceAnswer;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.SingleChoiceAnswer;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.TextAnswer;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 26.05.15.
 */

@Accessors(fluent = true)
@NoArgsConstructor(staticName = "instance")
public class AnswerQuestionnaireCommandBuilder {

    @Setter
    private QuestionnaireId questionnaireId = QuestionnaireId.of("questionId");
    @Setter
    private Attendee attendee = Attendee.of("firstname", "lastname");

    private final List<CheckableAnswer> defaultAnswers = ImmutableList.of(
            new MultipleChoiceAnswer(QuestionId.of("id1"), ImmutableList.of("opt1", "opt2")),
            new SingleChoiceAnswer(QuestionId.of("id2"), "opt1"),
            new TextAnswer(QuestionId.of("id3"), "answer")
    );

    private final List<CheckableAnswer> answers = Lists.newLinkedList();

    public AnswerQuestionnaireCommandBuilder answer(CheckableAnswer answer) {
        answers.add(answer);
        return this;
    }

    public AnswerQuestionnaireCommand build() {
        return new AnswerQuestionnaireCommand(questionnaireId, attendee, answers.isEmpty() ? defaultAnswers : answers);
    }
}

package knbit.events.bc.interest.questionnaire.domain.builders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.commands.VoteQuestionnaireUpCommand;
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
public class VoteQuestionnaireUpCommandBuilder {

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

    public VoteQuestionnaireUpCommandBuilder answer(CheckableAnswer answer) {
        answers.add(answer);
        return this;
    }

    public VoteQuestionnaireUpCommand build() {
        return new VoteQuestionnaireUpCommand(questionnaireId, attendee, answers.isEmpty() ? defaultAnswers : answers);
    }
}

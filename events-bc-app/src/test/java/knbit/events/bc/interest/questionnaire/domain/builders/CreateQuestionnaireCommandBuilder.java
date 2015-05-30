package knbit.events.bc.interest.questionnaire.domain.builders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.questionnaire.domain.enums.QuestionType;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.commands.CreateQuestionnaireCommand;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionData;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

/**
 * Created by novy on 26.05.15.
 */

@Accessors(fluent = true)
@NoArgsConstructor(staticName = "instance")
public class CreateQuestionnaireCommandBuilder {

    @Setter
    private QuestionnaireId questionnaireId = QuestionnaireId.of("questionId");
    @Setter
    private EventId eventId = EventId.of("eventId");

    private List<QuestionData> defaultQuestions = ImmutableList.of(
            QuestionData.of("q1", "q2", QuestionType.SINGLE_CHOICE, ImmutableList.of("ans1", "ans2")),
            QuestionData.of("q2", "q3", QuestionType.MULTIPLE_CHOICE, ImmutableList.of("ans1", "ans2")),
            QuestionData.of("q3", "d3", QuestionType.TEXT, Collections.emptyList())
    );

    private List<QuestionData> questions = Lists.newLinkedList();

    public CreateQuestionnaireCommandBuilder question(QuestionData questionData) {
        questions.add(questionData);
        return this;
    }

    public CreateQuestionnaireCommand build() {
        return CreateQuestionnaireCommand.of(
                questionnaireId, eventId, questions.isEmpty() ? defaultQuestions : questions
        );
    }

}
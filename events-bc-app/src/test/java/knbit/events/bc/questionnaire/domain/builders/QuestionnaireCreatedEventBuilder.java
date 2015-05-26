package knbit.events.bc.questionnaire.domain.builders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.questionnaire.domain.enums.QuestionType;
import knbit.events.bc.questionnaire.domain.valueobjects.events.QuestionnaireCreatedEvent;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionId;
import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import knbit.events.bc.questionnaire.domain.valueobjects.question.IdentifiedQuestionData;
import knbit.events.bc.questionnaire.domain.valueobjects.question.QuestionData;
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
public class QuestionnaireCreatedEventBuilder {

    @Setter
    private QuestionnaireId questionnaireId = QuestionnaireId.of("questionnaireId");
    @Setter
    private EventId eventId = EventId.of("eventId");

    private List<IdentifiedQuestionData> defaultQuestions = ImmutableList.of(
            IdentifiedQuestionData.of(
                    QuestionId.of("id1"),
                    QuestionData.of("q1", "q2", QuestionType.SINGLE_CHOICE, ImmutableList.of("ans1", "ans2"))
            ),
            IdentifiedQuestionData.of(
                    QuestionId.of("id2"),
                    QuestionData.of("q2", "q3", QuestionType.MULTIPLE_CHOICE, ImmutableList.of("ans1", "ans2"))
            ),
            IdentifiedQuestionData.of(
                    QuestionId.of("id3"),
                    QuestionData.of("q3", "d3", QuestionType.TEXT, Collections.emptyList())
            )
    );

    private List<IdentifiedQuestionData> questions = Lists.newLinkedList();

    public QuestionnaireCreatedEventBuilder question(QuestionId questionId, QuestionData questionData) {
        questions.add(
                IdentifiedQuestionData.of(questionId, questionData)
        );
        return this;
    }


    public QuestionnaireCreatedEvent build() {
        return QuestionnaireCreatedEvent.of(
                questionnaireId, eventId, questions.isEmpty() ? defaultQuestions : questions
        );
    }

}

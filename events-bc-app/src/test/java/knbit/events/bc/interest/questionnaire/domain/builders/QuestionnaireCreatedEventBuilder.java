package knbit.events.bc.interest.questionnaire.domain.builders;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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

/*    private List<IdentifiedQuestionData> defaultQuestions = ImmutableList.of(
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

    public QuestionnaireCreatedEventBuilder question(IdentifiedQuestionData questionData) {
        questions.add(questionData);
        return this;
    }


    public QuestionnaireCreatedEvent build() {
        return QuestionnaireCreatedEvent.of(
                questionnaireId, eventId, questions.isEmpty() ? defaultQuestions : questions
        );
    }*/

}

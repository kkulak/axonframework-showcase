package knbit.events.bc.interest.questionnaire.domain.builders;

import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 26.05.15.
 */
@Accessors(fluent = true)
@NoArgsConstructor(staticName = "instance")
public class QuestionnaireAnsweredEventBuilder {

    @Setter
    private QuestionnaireId questionnaireId = QuestionnaireId.of("questionId");
    @Setter
    private Attendee attendee = Attendee.of("firstname", "lastname");

/*    private final List<AnsweredQuestion> defaultAnsweredQuestions = ImmutableList.of(
            AnsweredQuestion.of(
                    QuestionId.of("id1"), QuestionTitle.of("title1"), QuestionDescription.of("desc1"),
                    QuestionType.MULTIPLE_CHOICE, ImmutableList.of(DomainAnswer.of("opt1"), DomainAnswer.of("opt2")),
                    ImmutableList.of(DomainAnswer.of("opt1"), DomainAnswer.of("opt2"))
            ),
            AnsweredQuestion.of(
                    QuestionId.of("id2"), QuestionTitle.of("title1"), QuestionDescription.of("desc2"),
                    QuestionType.SINGLE_CHOICE, ImmutableList.of(DomainAnswer.of("opt1"), DomainAnswer.of("opt2")),
                    ImmutableList.of(DomainAnswer.of("opt1"))
            ),
            AnsweredQuestion.of(
                    QuestionId.of("id3"), QuestionTitle.of("title3"), QuestionDescription.of("desc3"),
                    QuestionType.TEXT, Collections.emptyList(), ImmutableList.of(DomainAnswer.of("some answer"))
            )
    );

    private final List<AnsweredQuestion> answeredQuestions = Lists.newLinkedList();

    public QuestionnaireAnsweredEventBuilder answeredQuestion(AnsweredQuestion answeredQuestion) {
        answeredQuestions.add(answeredQuestion);
        return this;
    }

    public QuestionnaireAnsweredEvent build() {
        return new QuestionnaireAnsweredEvent(
                questionnaireId, attendee, answeredQuestions.isEmpty() ? defaultAnsweredQuestions : answeredQuestions
        );
    }*/
}

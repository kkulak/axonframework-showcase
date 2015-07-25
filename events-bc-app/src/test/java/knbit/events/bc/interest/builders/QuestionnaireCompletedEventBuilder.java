package knbit.events.bc.interest.builders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.events.QuestionnaireCompletedEvent;
import knbit.events.bc.interest.domain.enums.AnswerType;
import knbit.events.bc.common.domain.valueobjects.Attendee;
import knbit.events.bc.interest.domain.valueobjects.question.QuestionData;
import knbit.events.bc.interest.domain.valueobjects.question.QuestionDescription;
import knbit.events.bc.interest.domain.valueobjects.question.QuestionTitle;
import knbit.events.bc.interest.domain.valueobjects.question.answer.AnsweredQuestion;
import knbit.events.bc.interest.domain.valueobjects.question.answer.DomainAnswer;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 31.05.15.
 */

@Accessors(fluent = true)
@NoArgsConstructor(staticName = "instance")
public class QuestionnaireCompletedEventBuilder {

    @Setter
    private EventId eventId = EventId.of("eventId");
    @Setter
    private Attendee attendee = Attendee.of("firstname", "lastname");

    private List<AnsweredQuestion> defaultAnsweredQuestions = ImmutableList.of(
            AnsweredQuestion.of(
                    QuestionData.of(
                            QuestionTitle.of("title1"),
                            QuestionDescription.of("desc1"),
                            AnswerType.MULTIPLE_CHOICE,
                            ImmutableList.of(DomainAnswer.of("opt1"), DomainAnswer.of("opt2"))
                    ),
                    ImmutableList.of(DomainAnswer.of("opt1"), DomainAnswer.of("opt2"))
            ),
            AnsweredQuestion.of(
                    QuestionData.of(
                            QuestionTitle.of("title1"),
                            QuestionDescription.of("desc2"),
                            AnswerType.SINGLE_CHOICE,
                            ImmutableList.of(DomainAnswer.of("opt1"), DomainAnswer.of("opt2"))
                    ),
                    ImmutableList.of(DomainAnswer.of("opt1"))
            )
    );

    private List<AnsweredQuestion> answeredQuestions = Lists.newLinkedList();

    public QuestionnaireCompletedEventBuilder answeredQuestion(AnsweredQuestion answeredQuestion) {
        answeredQuestions.add(answeredQuestion);
        return this;
    }

    public QuestionnaireCompletedEvent build() {
        return QuestionnaireCompletedEvent.of(
                eventId,
                attendee,
                answeredQuestions.isEmpty() ? defaultAnsweredQuestions : answeredQuestions
        );
    }
}

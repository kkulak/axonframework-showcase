package knbit.events.bc.interest.builders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import knbit.events.bc.interest.questionnaire.domain.enums.AnswerType;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionData;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionDescription;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.QuestionTitle;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.question.answer.DomainAnswer;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 31.05.15.
 */

@Accessors(fluent = true)
@NoArgsConstructor(staticName = "instance")
public class QuestionDataBuilder {

    @Setter
    private QuestionTitle title = QuestionTitle.of("title");
    @Setter
    private QuestionDescription description = QuestionDescription.of("desc");
    @Setter
    private AnswerType answerType = AnswerType.SINGLE_CHOICE;

    private List<DomainAnswer> defaultAnswers = ImmutableList.of(
            DomainAnswer.of("opt1"), DomainAnswer.of("opt1")
    );

    private List<DomainAnswer> answers = Lists.newLinkedList();

    public QuestionDataBuilder answer(DomainAnswer answer) {
        answers.add(answer);
        return this;
    }

    public QuestionData build() {
        return QuestionData.of(title, description, answerType, answers.isEmpty() ? defaultAnswers : answers);
    }
}

package knbit.events.bc.interest.web.forms;

import knbit.events.bc.interest.domain.valueobjects.question.QuestionData;
import knbit.events.bc.interest.domain.valueobjects.question.QuestionDescription;
import knbit.events.bc.interest.domain.valueobjects.question.QuestionTitle;
import knbit.events.bc.interest.domain.valueobjects.question.answer.DomainAnswer;

import java.util.List;
import java.util.stream.Collectors;

public class MappingUtils {

    public static List<QuestionData> toQuestionData(List<QuestionDataDTO> viewModelRepresentation) {
        return viewModelRepresentation
                .stream()
                .map(MappingUtils::toQuestionData)
                .collect(Collectors.toList());
    }

    public static QuestionData toQuestionData(QuestionDataDTO dto) {
        return QuestionData.of(
                QuestionTitle.of(dto.getTitle()),
                QuestionDescription.of(dto.getDescription()),
                dto.getType(),
                toDomainAnswers(dto.getAnswers())
        );
    }

    public static List<DomainAnswer> toDomainAnswers(List<String> answers) {
        return answers.stream()
                .map(DomainAnswer::of)
                .collect(Collectors.toList());
    }

}

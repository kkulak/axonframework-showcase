package knbit.events.bc.questionnaire.domain.valueobjects.vote;

import knbit.events.bc.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import knbit.events.bc.questionnaire.domain.valueobjects.Attendee;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by novy on 25.05.15.
 */

@Accessors(fluent = true)
@Getter
@RequiredArgsConstructor
public class Vote {

    private final Attendee attendee;
    private final QuestionnaireId questionnaireId;

}

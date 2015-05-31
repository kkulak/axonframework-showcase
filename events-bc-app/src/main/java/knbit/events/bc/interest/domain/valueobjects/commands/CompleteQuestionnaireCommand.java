package knbit.events.bc.interest.domain.valueobjects.commands;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.AttendeeAnswer;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 31.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class CompleteQuestionnaireCommand {

    private final EventId eventId;
    private final AttendeeAnswer answer;

}

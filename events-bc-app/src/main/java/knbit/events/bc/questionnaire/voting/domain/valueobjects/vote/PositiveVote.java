package knbit.events.bc.questionnaire.voting.domain.valueobjects.vote;

import knbit.events.bc.questionnaire.voting.domain.valueobjects.QuestionnaireId;
import knbit.events.bc.questionnaire.voting.domain.valueobjects.Attendee;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 25.05.15.
 */

@Accessors(fluent = true)
@Value
@EqualsAndHashCode(callSuper = false)
public class PositiveVote extends Vote {


    public PositiveVote(Attendee attendee, QuestionnaireId questionnaireId) {
        super(attendee, questionnaireId);
    }
}

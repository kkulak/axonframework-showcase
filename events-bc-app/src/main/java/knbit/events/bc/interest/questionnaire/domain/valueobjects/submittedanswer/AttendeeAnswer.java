package knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer;

import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.submittedanswer.CheckableAnswer;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by novy on 25.05.15.
 */

@Accessors(fluent = true)
@Value
public class AttendeeAnswer {

    private final Attendee attendee;
    private final List<CheckableAnswer> answers;

    public AttendeeAnswer(Attendee attendee, List<CheckableAnswer> answers) {
        this.attendee = attendee;
        this.answers = answers;
    }
}

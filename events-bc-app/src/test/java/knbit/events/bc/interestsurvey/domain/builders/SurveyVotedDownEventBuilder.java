package knbit.events.bc.interestsurvey.domain.builders;

import knbit.events.bc.interestsurvey.domain.valueobjects.SurveyId;
import knbit.events.bc.interestsurvey.domain.valueobjects.events.SurveyVotedDownEvent;
import knbit.events.bc.questionnaire.domain.valueobjects.Attendee;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "instance")
public class SurveyVotedDownEventBuilder {

    private SurveyId surveyId = SurveyId.of("surveyId");
    private Attendee attendee = Attendee.of("firstname", "lastname");

    public SurveyVotedDownEvent build() {
        return new SurveyVotedDownEvent(surveyId, attendee);
    }

}

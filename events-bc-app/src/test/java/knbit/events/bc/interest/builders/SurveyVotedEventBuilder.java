package knbit.events.bc.interest.builders;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.enums.VoteType;
import knbit.events.bc.interest.domain.valueobjects.Vote;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyVotedEvent;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 28.05.15.
 */

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "instance")
public class SurveyVotedEventBuilder {

    private EventId eventId = EventId.of("eventId");
    private Vote vote = Vote.of(
            Attendee.of("firstname", "lastname"), VoteType.POSITIVE
    );


    public SurveyVotedEvent build() {
        return SurveyVotedEvent.of(eventId, vote);
    }

}

package knbit.events.bc.interest.questionnaire.domain.valueobjects.events;

import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;

/**
 * Created by novy on 26.05.15.
 */
public interface QuestionnaireVotedEvent {

    Attendee attendee();
}

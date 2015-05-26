package knbit.events.bc.questionnaire.domain.valueobjects.events;

import knbit.events.bc.questionnaire.domain.valueobjects.Attendee;

/**
 * Created by novy on 26.05.15.
 */
public interface QuestionnaireVotedEvent {

    Attendee attendee();
}

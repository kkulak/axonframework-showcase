package knbit.events.bc.interestsurvey.domain.aggreagates;

import com.google.common.collect.Sets;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interestsurvey.domain.exceptions.SurveyAlreadyVotedException;
import knbit.events.bc.interestsurvey.domain.policies.InterestPolicy;
import knbit.events.bc.interestsurvey.domain.valueobjects.SurveyId;
import knbit.events.bc.interestsurvey.domain.valueobjects.events.InterestThresholdReachedEvent;
import knbit.events.bc.interestsurvey.domain.valueobjects.events.SurveyVotedDownEvent;
import knbit.events.bc.interestsurvey.domain.valueobjects.events.SurveyVotedUpEvent;
import knbit.events.bc.interestsurvey.domain.valueobjects.events.surveycreation.SurveyCreatedEvent;
import knbit.events.bc.interestsurvey.domain.valueobjects.events.surveycreation.SurveyCreatedEventFactory;
import knbit.events.bc.questionnaire.domain.valueobjects.Attendee;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.Collection;

/**
 * Created by novy on 28.05.15.
 */
public class Survey extends IdentifiedDomainAggregateRoot<SurveyId> {

    private EventId eventId;
    private Collection<Attendee> voters = Sets.newHashSet();
    private int actualInterest;

    private InterestPolicy interestPolicy;

    private Survey() {
    }

    public Survey(SurveyId surveyId, EventId eventId,
                  InterestPolicy interestPolicy,
                  SurveyCreatedEventFactory surveyCreatedEventFactory) {
        this.interestPolicy = interestPolicy;

        apply(
                surveyCreatedEventFactory.newSurveyCreatedEvent(surveyId, eventId, interestPolicy)
        );
    }

    @EventSourcingHandler
    private void on(SurveyCreatedEvent event) {
        this.id = event.surveyId();
        this.eventId = event.eventId();
        this.interestPolicy = event.interestPolicy();

        this.actualInterest = 0;
    }

    public void voteUp(Attendee attendee) {
        checkIfAttendeeAlreadyVoted(attendee);

        if (interestPolicy.reachedBy(actualInterest + 1)) {
            apply(
                    new InterestThresholdReachedEvent(id)
            );
        }

        apply(
                new SurveyVotedUpEvent(id, attendee)
        );

    }

    public void voteDown(Attendee attendee) {
        checkIfAttendeeAlreadyVoted(attendee);

        apply(
                new SurveyVotedDownEvent(id, attendee)
        );

    }

    @EventSourcingHandler
    private void on(SurveyVotedUpEvent event) {
        voters.add(event.attendee());
        actualInterest++;
    }

    @EventSourcingHandler
    private void on(SurveyVotedDownEvent event) {
        voters.add(event.attendee());
    }

    private void checkIfAttendeeAlreadyVoted(Attendee attendee) {
        if (voters.contains(attendee)) {
            throw new SurveyAlreadyVotedException(id, attendee);
        }
    }
}

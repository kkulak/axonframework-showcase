package knbit.events.bc.interest.survey.domain.aggreagates;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.backlogevent.domain.valueobjects.EventId;
import knbit.events.bc.interest.common.domain.enums.State;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.interest.survey.domain.exceptions.CannotVoteOnClosedSurveyException;
import knbit.events.bc.interest.survey.domain.exceptions.SurveyAlreadyVotedException;
import knbit.events.bc.interest.survey.domain.policies.InterestPolicy;
import knbit.events.bc.interest.survey.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.survey.domain.valueobjects.events.InterestThresholdReachedEvent;
import knbit.events.bc.interest.survey.domain.valueobjects.events.SurveyClosedEvent;
import knbit.events.bc.interest.survey.domain.valueobjects.events.SurveyVotedDownEvent;
import knbit.events.bc.interest.survey.domain.valueobjects.events.SurveyVotedUpEvent;
import knbit.events.bc.interest.survey.domain.valueobjects.events.surveycreation.SurveyCreatedEvent;
import knbit.events.bc.interest.survey.domain.valueobjects.events.surveycreation.SurveyCreatedEventFactory;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.Collection;

/**
 * Created by novy on 28.05.15.
 */
public class Survey extends IdentifiedDomainAggregateRoot<SurveyId> {

    private EventId eventId;
    private Collection<Attendee> voters = Sets.newHashSet();
    private int actualInterest;
    private State state;

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
        this.state = State.PENDING;
    }

    public void voteUp(Attendee attendee) {
        rejectOnClosed();
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
        rejectOnClosed();
        checkIfAttendeeAlreadyVoted(attendee);

        apply(
                new SurveyVotedDownEvent(id, attendee)
        );

    }

    public void close() {
        Preconditions.checkState(state == State.PENDING, "Already closed!");
        apply(new SurveyClosedEvent(id));
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

    @EventSourcingHandler
    private void on(SurveyClosedEvent event) {
        this.state = State.CLOSED;
    }

    private void checkIfAttendeeAlreadyVoted(Attendee attendee) {
        if (voters.contains(attendee)) {
            throw new SurveyAlreadyVotedException(id, attendee);
        }
    }

    private void rejectOnClosed() {
        if (state == State.CLOSED) {
            throw new CannotVoteOnClosedSurveyException(id);
        }

    }
}

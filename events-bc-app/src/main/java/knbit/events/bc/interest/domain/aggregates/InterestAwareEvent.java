package knbit.events.bc.interest.domain.aggregates;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.common.domain.enums.State;
import knbit.events.bc.interest.domain.exceptions.SurveyAlreadyVotedException;
import knbit.events.bc.interest.domain.valueobjects.events.*;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.interest.survey.domain.policies.InterestPolicy;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.Collection;

/**
 * Created by novy on 28.05.15.
 */
public class InterestAwareEvent extends IdentifiedDomainAggregateRoot<EventId> {

    private EventDetails eventDetails;

    private Collection<Attendee> positiveVoters = Sets.newHashSet();
    private Collection<Attendee> negativeVoters = Sets.newHashSet();
    private InterestPolicy interestPolicy;

    private State state;

    private InterestAwareEvent() {
    }

    public InterestAwareEvent(EventId eventId, EventDetails eventDetails) {

        apply(
                InterestAwareEventCreated.of(eventId, eventDetails)
        );
    }


    @EventSourcingHandler
    private void on(InterestAwareEventCreated event) {
        this.id = event.eventId();
        this.eventDetails = event.eventDetails();

        this.state = State.PENDING;
    }

    public void voteUp(Attendee attendee) {
        rejectOnClosed();
        checkIfAttendeeAlreadyVoted(attendee);

        if (interestPolicy.reachedBy(positiveVoters.size() + 1)) {
            apply(InterestThresholdReachedEvent.of(id));
        }

        apply(SurveyVotedUpEvent.of(id, attendee));

    }

    public void voteDown(Attendee attendee) {
        rejectOnClosed();
        checkIfAttendeeAlreadyVoted(attendee);

        apply(SurveyVotedDownEvent.of(id, attendee));
    }

    public void close() {
        Preconditions.checkState(state == State.PENDING, "Already closed!");
//        apply(new SurveyClosedEvent(id));
    }

    @EventSourcingHandler
    private void on(SurveyingStartedEvent event) {
        this.interestPolicy = event.interestPolicy();
    }

    @EventSourcingHandler
    private void on(SurveyVotedUpEvent event) {
        positiveVoters.add(event.attendee());
    }

    @EventSourcingHandler
    private void on(SurveyVotedDownEvent event) {
        negativeVoters.add(event.attendee());
    }

    private void checkIfAttendeeAlreadyVoted(Attendee attendee) {
        if (attendeeAlreadyVoted(attendee)) {
            throw new SurveyAlreadyVotedException(id, attendee);
        }
    }

    private boolean attendeeAlreadyVoted(Attendee attendee) {
        return votedUp(attendee) || votedDown(attendee);
    }

    private boolean votedUp(Attendee attendee) {
        return positiveVoters.contains(attendee);
    }

    private boolean votedDown(Attendee attendee) {
        return negativeVoters.contains(attendee);
    }




    private void rejectOnClosed() {
//        if (state == State.CLOSED) {
//            throw new CannotVoteOnClosedSurveyException(id);
//        }

    }
}

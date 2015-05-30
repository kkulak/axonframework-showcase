package knbit.events.bc.interest.domain.aggregates;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import knbit.events.bc.common.domain.IdentifiedDomainAggregateRoot;
import knbit.events.bc.common.domain.valueobjects.EventDetails;
import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.interest.common.domain.enums.State;
import knbit.events.bc.interest.domain.enums.VoteType;
import knbit.events.bc.interest.domain.exceptions.SurveyAlreadyVotedException;
import knbit.events.bc.interest.domain.valueobjects.Vote;
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEventCreated;
import knbit.events.bc.interest.domain.valueobjects.events.InterestThresholdReachedEvent;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyVotedEvent;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyingStartedEvent;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.Attendee;
import knbit.events.bc.interest.survey.domain.policies.InterestPolicy;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import java.util.Collection;

/**
 * Created by novy on 28.05.15.
 */
public class InterestAwareEvent extends IdentifiedDomainAggregateRoot<EventId> {

    private EventDetails eventDetails;

    private Collection<Vote> votes = Sets.newHashSet();
    private int actualInterest;
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

        this.actualInterest = 0;
        this.state = State.PENDING;
    }

    @EventSourcingHandler
    private void on(SurveyingStartedEvent event) {
        this.interestPolicy = event.interestPolicy();
    }

    public void voteUp(Attendee attendee) {
        rejectOnClosed();
        checkIfAttendeeAlreadyVoted(attendee);

        if (interestPolicy.reachedBy(actualInterest + 1)) {
            apply(
                    InterestThresholdReachedEvent.of(id)
            );
        }

        apply(
                SurveyVotedEvent.of(
                        id, Vote.of(attendee, VoteType.POSITIVE)
                )
        );

    }

    public void voteDown(Attendee attendee) {
        rejectOnClosed();
        checkIfAttendeeAlreadyVoted(attendee);
//
//        apply(
//                new SurveyVotedDownEvent(id, attendee)
//        );

    }

    public void close() {
        Preconditions.checkState(state == State.PENDING, "Already closed!");
//        apply(new SurveyClosedEvent(id));
    }

    @EventSourcingHandler
    private void on(SurveyVotedEvent event) {
        votes.add(event.vote());
        actualInterest++;
    }

    private void checkIfAttendeeAlreadyVoted(Attendee attendee) {
        if (attendeeAlreadyVoted(attendee)) {
            throw new SurveyAlreadyVotedException(id, attendee);
        }
    }

    private boolean attendeeAlreadyVoted(Attendee attendee) {
        return votes
                .stream()
                .anyMatch(vote -> vote.attendee().equals(attendee));
    }


    private void rejectOnClosed() {
//        if (state == State.CLOSED) {
//            throw new CannotVoteOnClosedSurveyException(id);
//        }

    }
}

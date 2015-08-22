package knbit.events.bc.interest.domain;

import knbit.events.bc.interest.domain.aggregates.InterestAwareEvent;
import knbit.events.bc.interest.domain.valueobjects.commands.*;
import knbit.events.bc.interest.domain.valueobjects.events.surveystarting.factory.BasicSurveyStartedEventFactory;
import knbit.events.bc.interest.domain.valueobjects.events.surveystarting.factory.SurveyStartedEventFactory;
import knbit.events.bc.interest.domain.valueobjects.events.surveystarting.factory.SurveyWithEndingDateStartedEventFactory;
import knbit.events.bc.interest.domain.policies.surveyinginterest.InterestPolicy;
import knbit.events.bc.interest.domain.policies.surveyinginterest.InterestThresholdTurnedOffPolicy;
import knbit.events.bc.interest.domain.policies.surveyinginterest.WithFixedThresholdPolicy;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by novy on 28.05.15.
 */

@Component
public class InterestAwareEventCommandHandler {

    // todo: multiple handlers pls?

    private final Repository<InterestAwareEvent> repository;

    @Autowired
    public InterestAwareEventCommandHandler(@Qualifier("interestAwareEventRepository") Repository<InterestAwareEvent> repository) {
        this.repository = repository;
    }

    @CommandHandler
    public void handle(InterestAwareEventCommands.Create command) {
        final InterestAwareEvent interestAwareEvent = new InterestAwareEvent(
                command.eventId(), command.eventDetails()
        );
        repository.add(interestAwareEvent);
    }

    @CommandHandler
    public void handle(SurveyCommands.Start command) {
        final InterestPolicy interestPolicy =
                createInterestThresholdPolicy(command.minimalInterestThreshold());

        final SurveyStartedEventFactory factory =
                createSurveyCreatedEventFactory(command.endingSurveyDate());

        final InterestAwareEvent interestAwareEvent = repository.load(command.eventId());
        interestAwareEvent.startSurveying(
                interestPolicy, factory
        );

    }

    @CommandHandler
    public void handle(SurveyCommands.VoteUp command) {
        final InterestAwareEvent interestAwareEvent = repository.load(command.eventId());

        interestAwareEvent.voteUp(command.attendee());
    }

    @CommandHandler
    public void handle(SurveyCommands.VoteDown command) {
        final InterestAwareEvent interestAwareEvent = repository.load(command.eventId());

        interestAwareEvent.voteDown(command.attendee());
    }

    private static InterestPolicy createInterestThresholdPolicy(Optional<Integer> minimalInterestThreshold) {
        return minimalInterestThreshold.isPresent() ?
                new WithFixedThresholdPolicy(minimalInterestThreshold.get()) : new InterestThresholdTurnedOffPolicy();
    }

    private static SurveyStartedEventFactory createSurveyCreatedEventFactory(Optional<DateTime> endingSurveyDate) {
        return endingSurveyDate.isPresent() ?
                new SurveyWithEndingDateStartedEventFactory(endingSurveyDate.get()) : new BasicSurveyStartedEventFactory();
    }

    @CommandHandler
    public void handle(QuestionnaireCommands.End command) {
        final InterestAwareEvent interestAwareEvent = repository.load(command.eventId());
        interestAwareEvent.endSurveying();
    }

    @CommandHandler
    public void handle(QuestionnaireCommands.Add command) {
        final InterestAwareEvent interestAwareEvent = repository.load(command.eventId());
        interestAwareEvent.addQuestionnaire(command.questions());
    }

    @CommandHandler
    public void handle(QuestionnaireCommands.Complete command) {
        final InterestAwareEvent interestAwareEvent = repository.load(command.eventId());
        interestAwareEvent.completeQuestionnaire(command.answer());
    }

    @CommandHandler
    public void handle(InterestAwareEventCommands.TransitToUnderTermChoosingEvent command) {
        final InterestAwareEvent interestAwareEvent = repository.load(command.eventId());
        interestAwareEvent.transitToUnderChoosingTermEvent();
    }
}

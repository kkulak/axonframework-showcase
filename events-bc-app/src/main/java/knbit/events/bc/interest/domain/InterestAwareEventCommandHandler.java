package knbit.events.bc.interest.domain;

import knbit.events.bc.interest.domain.aggregates.InterestAwareEvent;
import knbit.events.bc.interest.domain.aggregates.SurveyFactory;
import knbit.events.bc.interest.domain.valueobjects.commands.*;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 28.05.15.
 */

@Component
public class InterestAwareEventCommandHandler {

    private final Repository<InterestAwareEvent> repository;

    @Autowired
    public InterestAwareEventCommandHandler(@Qualifier("interestAwareEventRepository") Repository<InterestAwareEvent> repository) {
        this.repository = repository;
    }

//    @CommandHandler
//    public void handle(CreateSurveyCommand command) {
//        final InterestAwareEvent interestAwareEvent = SurveyFactory.newSurvey(
//                command.surveyId(), command.eventId(),
//                command.minimalInterestThreshold(), command.endingSurveyDate()
//        );
//
//        repository.add(interestAwareEvent);
//    }

    @CommandHandler
    public void handle(CreateInterestAwareEventCommand command) {
        final InterestAwareEvent interestAwareEvent = new InterestAwareEvent(
                command.eventId(), command.eventDetails()
        );
        repository.add(interestAwareEvent);
    }

    @CommandHandler
    public void handle(VoteUpCommand command) {
        final InterestAwareEvent interestAwareEvent = repository.load(command.surveyId());

        interestAwareEvent.voteUp(command.attendee());
    }

    @CommandHandler
    public void handle(VoteDownCommand command) {
        final InterestAwareEvent interestAwareEvent = repository.load(command.surveyId());

        interestAwareEvent.voteDown(command.attendee());
    }

    @CommandHandler
    public void handle(CloseSurveyCommand command) {
        final InterestAwareEvent interestAwareEvent = repository.load(command.surveyId());
        interestAwareEvent.close();
    }
}

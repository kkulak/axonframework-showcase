package knbit.events.bc.interest.survey.domain;

import knbit.events.bc.interest.survey.domain.aggreagates.Survey;
import knbit.events.bc.interest.survey.domain.aggreagates.SurveyFactory;
import knbit.events.bc.interest.survey.domain.valueobjects.commands.CloseSurveyCommand;
import knbit.events.bc.interest.survey.domain.valueobjects.commands.CreateSurveyCommand;
import knbit.events.bc.interest.survey.domain.valueobjects.commands.VoteDownCommand;
import knbit.events.bc.interest.survey.domain.valueobjects.commands.VoteUpCommand;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 28.05.15.
 */

@Component
public class SurveyCommandHandler {

    private final Repository<Survey> repository;

    @Autowired
    public SurveyCommandHandler(@Qualifier("surveyRepository") Repository<Survey> repository) {
        this.repository = repository;
    }

    @CommandHandler
    public void handle(CreateSurveyCommand command) {
        final Survey survey = SurveyFactory.newSurvey(
                command.surveyId(), command.eventId(),
                command.minimalInterestThreshold(), command.endingSurveyDate()
        );

        repository.add(survey);
    }

    @CommandHandler
    public void handle(VoteUpCommand command) {
        final Survey survey = repository.load(command.surveyId());

        survey.voteUp(command.attendee());
    }

    @CommandHandler
    public void handle(VoteDownCommand command) {
        final Survey survey = repository.load(command.surveyId());

        survey.voteDown(command.attendee());
    }

    @CommandHandler
    public void handle(CloseSurveyCommand command) {
        final Survey survey = repository.load(command.surveyId());
        survey.close();
    }
}

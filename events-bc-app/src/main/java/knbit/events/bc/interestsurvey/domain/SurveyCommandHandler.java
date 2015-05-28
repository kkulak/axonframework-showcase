package knbit.events.bc.interestsurvey.domain;

import knbit.events.bc.interestsurvey.domain.aggreagates.Survey;
import knbit.events.bc.interestsurvey.domain.aggreagates.SurveyFactory;
import knbit.events.bc.interestsurvey.domain.valueobjects.commands.CreateSurveyCommand;
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
}

package knbit.events.bc.questionnaire.domain;

import knbit.events.bc.questionnaire.domain.aggregates.Questionnaire;
import knbit.events.bc.questionnaire.domain.aggregates.QuestionnaireFactory;
import knbit.events.bc.questionnaire.domain.valueobjects.commands.CreateQuestionnaireCommand;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 26.05.15.
 */

@Component
public class QuestionnaireCommandHandler {

    private final Repository<Questionnaire> repository;

    @Autowired
    public QuestionnaireCommandHandler(@Qualifier("questionnaireRepository") Repository<Questionnaire> repository) {
        this.repository = repository;
    }

    // todo: move creating questionnaire to Event itself
    @CommandHandler
    public void handle(CreateQuestionnaireCommand command) {
        final Questionnaire newQuestionnaire = QuestionnaireFactory.newQuestionnaire(
                command.questionnaireId(), command.eventId(), command.questions()
        );

        repository.add(newQuestionnaire);
    }
}

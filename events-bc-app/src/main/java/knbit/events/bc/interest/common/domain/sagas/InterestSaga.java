package knbit.events.bc.interest.common.domain.sagas;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.event.domain.valueobjects.events.EventCreated;
import knbit.events.bc.interest.common.domain.valueobjects.events.SurveyingInterestStoppedEvent;
import knbit.events.bc.interest.common.domain.valueobjects.events.SurveyingTimeExceededEvent;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.commands.CloseQuestionnaireCommand;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.events.QuestionnaireCreatedEvent;
import knbit.events.bc.interest.questionnaire.domain.valueobjects.ids.QuestionnaireId;
import knbit.events.bc.interest.domain.valueobjects.SurveyId;
import knbit.events.bc.interest.domain.valueobjects.commands.CloseSurveyCommand;
import knbit.events.bc.interest.domain.valueobjects.events.surveycreation.SurveyCreatedEvent;
import knbit.events.bc.interest.domain.valueobjects.events.surveycreation.SurveyWithEndingDateCreatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.ScheduleToken;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Created by novy on 28.05.15.
 */
public class InterestSaga extends AbstractAnnotatedSaga {

    private static final String EVENT_ID_PROPERTY = "eventId";

    private EventId eventId;
    private SurveyId surveyId;
    private Optional<QuestionnaireId> questionnaireId = Optional.empty();

    private transient EventScheduler eventScheduler;
    private transient CommandGateway commandGateway;
    private ScheduleToken scheduleToken;

    @StartSaga
    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    public void on(EventCreated event) {
        eventId = event.eventId();
    }

    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    public void on(SurveyCreatedEvent event) {
        surveyId = event.surveyId();

    }

    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    public void on(SurveyWithEndingDateCreatedEvent event) {
        surveyId = event.surveyId();

        scheduleToken = eventScheduler.schedule(
                event.endingSurveyDate(),
                new SurveyingTimeExceededEvent(
                        eventId, event.endingSurveyDate()
                )
        );
    }

    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    public void on(QuestionnaireCreatedEvent event) {
        questionnaireId = Optional.of(event.questionnaireId());
    }

    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    public void on(SurveyingTimeExceededEvent event) {
        closeSurveyAndQuestionnaire();
        end();
    }

    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    public void on(SurveyingInterestStoppedEvent event) {
        closeSurveyAndQuestionnaire();
        end();
    }

    private void closeSurveyAndQuestionnaire() {
        commandGateway.send(
                new CloseSurveyCommand(surveyId)
        );
        if (questionnaireId.isPresent()) {
            commandGateway.send(
                    new CloseQuestionnaireCommand(questionnaireId.get())
            );
        }
    }

    @Autowired
    public void setEventScheduler(EventScheduler eventScheduler) {
        this.eventScheduler = eventScheduler;
    }

    @Autowired
    public void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }
}

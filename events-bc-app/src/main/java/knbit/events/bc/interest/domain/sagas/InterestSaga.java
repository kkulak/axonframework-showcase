package knbit.events.bc.interest.domain.sagas;

import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.valueobjects.SurveyingTimeExceededEvent;
import knbit.events.bc.interest.domain.valueobjects.commands.QuestionnaireCommands;
import knbit.events.bc.interest.domain.valueobjects.events.SurveyEvents;
import knbit.events.bc.interest.domain.valueobjects.events.surveystarting.SurveyStartingEvents;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.ScheduleToken;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by novy on 28.05.15.
 */
public class InterestSaga extends AbstractAnnotatedSaga {

    private static final String EVENT_ID_PROPERTY = "eventId";

    private EventId eventId;

    private transient EventScheduler eventScheduler;
    private transient CommandGateway commandGateway;
    private ScheduleToken scheduleToken;

    @StartSaga
    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    public void on(SurveyStartingEvents.StartedWithEndingDate event) {

        this.eventId = event.eventId();

        scheduleToken = eventScheduler.schedule(
                event.endingSurveyDate(),
                SurveyingTimeExceededEvent.of(
                        eventId, event.endingSurveyDate()
                )
        );
    }

    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    public void on(SurveyingTimeExceededEvent event) {
        commandGateway.send(
                QuestionnaireCommands.End.of(eventId)
        );
        end();
    }

    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    public void on(SurveyEvents.Ended event) {
        eventScheduler.cancelSchedule(scheduleToken);
        end();
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

package knbit.events.bc.interest.common.domain.sagas;

import knbit.events.bc.event.domain.valueobjects.EventId;
import knbit.events.bc.event.domain.valueobjects.events.EventCreated;
import knbit.events.bc.interest.common.domain.valueobjects.events.SurveyingTimeExceededEvent;
import knbit.events.bc.interest.survey.domain.valueobjects.events.surveycreation.SurveyCreatedEvent;
import knbit.events.bc.interest.survey.domain.valueobjects.events.surveycreation.SurveyWithEndingDateCreatedEvent;
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
    public void on(EventCreated event) {
        eventId = event.eventId();
    }

    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    public void on(SurveyCreatedEvent event) {

    }

    @SagaEventHandler(associationProperty = EVENT_ID_PROPERTY)
    public void on(SurveyWithEndingDateCreatedEvent event) {
        scheduleToken = eventScheduler.schedule(
                event.endingSurveyDate(),
                new SurveyingTimeExceededEvent(
                        eventId, event.endingSurveyDate()
                )
        );

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

package knbit.events.bc.eventproposal.domain.sagas;

import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;
import knbit.events.bc.eventproposal.domain.valueobjects.events.EventProposed;
import knbit.events.bc.eventproposal.domain.valueobjects.events.ProposalRejectedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.ScheduleToken;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by novy on 24.05.15.
 */

public class TimeAwareSaga extends AbstractAnnotatedSaga {

    private EventProposalId eventProposalId;

    private transient EventScheduler eventScheduler;

    private transient CommandGateway commandGateway;

    private ScheduleToken scheduleToken;
    @StartSaga
    @SagaEventHandler(associationProperty = "eventProposalId")
    public void handle(EventProposed event) {
        this.eventProposalId = event.eventProposalId();
        scheduleToken = eventScheduler.schedule(Duration.standardDays(5), new TimeExceeded(eventProposalId));
    }

    @SagaEventHandler(associationProperty = "eventProposalId")
    public void handle(ProposalRejectedEvent event) {
        eventScheduler.cancelSchedule(scheduleToken);
    }

    @SagaEventHandler(associationProperty = "eventProposalId")
    public void handle(TimeExceeded timeExceededEvent) {
        commandGateway.send(new DoSomethingCommand(eventProposalId));
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

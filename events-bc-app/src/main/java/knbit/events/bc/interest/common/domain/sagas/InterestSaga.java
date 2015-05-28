package knbit.events.bc.interest.common.domain.sagas;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by novy on 28.05.15.
 */
public class InterestSaga extends AbstractAnnotatedSaga {

    private transient EventScheduler eventScheduler;
    private transient CommandGateway commandGateway;

    @Autowired
    public void setEventScheduler(EventScheduler eventScheduler) {
        this.eventScheduler = eventScheduler;
    }

    @Autowired
    public void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }
}

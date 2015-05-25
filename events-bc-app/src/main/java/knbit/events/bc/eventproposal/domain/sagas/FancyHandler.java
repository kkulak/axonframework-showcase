package knbit.events.bc.eventproposal.domain.sagas;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 24.05.15.
 */

@Component
public class FancyHandler {

    @EventHandler
    public void on(TimeExceeded event) {
        System.out.println("time exceeded! " + event.getEventProposalId());
    }
}

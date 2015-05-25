package knbit.events.bc.eventproposal.domain.sagas;

import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;

/**
 * Created by novy on 25.05.15.
 */
public class DoSomethingCommand {
    private final EventProposalId eventProposalId;

    public DoSomethingCommand(EventProposalId eventProposalId) {
        this.eventProposalId = eventProposalId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

package knbit.events.bc.eventproposal.domain.sagas;

import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;

/**
 * Created by novy on 24.05.15.
 */
public class TimeExceeded {
    private final EventProposalId eventProposalId;

    public TimeExceeded(EventProposalId eventProposalId) {
        this.eventProposalId = eventProposalId;
    }

    public EventProposalId getEventProposalId() {
        return eventProposalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeExceeded that = (TimeExceeded) o;

        return !(eventProposalId != null ? !eventProposalId.equals(that.eventProposalId) : that.eventProposalId != null);

    }

    @Override
    public int hashCode() {
        return eventProposalId != null ? eventProposalId.hashCode() : 0;
    }
}

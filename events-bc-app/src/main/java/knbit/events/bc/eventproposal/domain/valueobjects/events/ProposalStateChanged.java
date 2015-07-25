package knbit.events.bc.eventproposal.domain.valueobjects.events;

import knbit.events.bc.eventproposal.domain.enums.ProposalState;
import knbit.events.bc.eventproposal.domain.valueobjects.EventProposalId;

/**
 * Created by novy on 06.05.15.
 */
public interface ProposalStateChanged {

    EventProposalId eventProposalId();

    ProposalState state();
}

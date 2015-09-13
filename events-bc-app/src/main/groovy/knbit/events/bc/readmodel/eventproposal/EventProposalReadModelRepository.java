package knbit.events.bc.readmodel.eventproposal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by novy on 06.05.15.
 */

@Repository
public interface EventProposalReadModelRepository
        extends CrudRepository<EventProposalViewModel, Long> {

    EventProposalViewModel findByDomainId(String domainId);
}

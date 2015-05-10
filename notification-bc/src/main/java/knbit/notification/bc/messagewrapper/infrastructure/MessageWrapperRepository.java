package knbit.notification.bc.messagewrapper.infrastructure;

import knbit.notification.bc.messagewrapper.domain.MessageWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageWrapperRepository extends JpaRepository<MessageWrapper, String> {
}

package knbit.notification.bc.messagewrapper.repository;

import knbit.notification.bc.messagewrapper.domain.MessageWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageWrapperRepository extends JpaRepository<MessageWrapper, String> {
}

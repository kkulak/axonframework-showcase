package knbit.notification.bc.messagewrapper.infrastructure.persistence;

import knbit.notification.bc.messagewrapper.domain.MessageWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageWrapperRepository extends JpaRepository<MessageWrapper, String> {

    @Query("SELECT mw FROM MessageWrapper mw ORDER BY mw.createdOn DESC")
    List<MessageWrapper> findOldMessages(Pageable pageable);

}

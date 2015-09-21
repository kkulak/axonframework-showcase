package knbit.events.bc.announcement.configuration;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by novy on 19.09.15.
 */
@NoRepositoryBean
public interface PublisherConfigurationRepository<T extends PublisherConfiguration, ID extends Serializable>
        extends Repository<T, ID> {

    Optional<T> findOne(ID id);

    Collection<T> findAll();

    <S extends T> S save(S entity);

    void delete(ID id);
}

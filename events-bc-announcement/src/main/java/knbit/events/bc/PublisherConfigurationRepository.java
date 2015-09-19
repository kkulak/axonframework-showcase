package knbit.events.bc;

import knbit.events.bc.announcement.PublisherConfiguration;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by novy on 19.09.15.
 */
public interface PublisherConfigurationRepository<T extends PublisherConfiguration, ID extends Serializable>
        extends Repository<T, ID> {

    Optional<T> findOne(ID id);

    Collection<T> findAll();

    <S extends T> S save(S entity);

    void delete(ID id);
}

package knbit.events.bc.common.viewmodel.allevents.infrastructure

import com.mongodb.DB
import com.mongodb.DBCollection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by novy on 29.06.15.
 */
@Configuration("all-events-repository-configuration")
public class RepositoryConfig {

    @Bean(name = "all-events")
    def DBCollection allEventsCollection(DB db) {
        db.allEventsCollection
    }

}

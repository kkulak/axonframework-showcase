package knbit.events.bc.readmodel.members.dashboard

import com.mongodb.DB
import com.mongodb.DBCollection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by novy on 20.10.15.
 */
@Configuration("survey-repository-configuration")
class RepositoryConfig {

    @Bean(name = "dashboard-events")
    def DBCollection surveyEventsCollection(DB db) {
        db.dashboardEventCollection
    }
}

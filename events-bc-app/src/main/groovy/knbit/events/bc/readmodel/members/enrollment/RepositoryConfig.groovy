package knbit.events.bc.readmodel.members.enrollment

import com.mongodb.DB
import com.mongodb.DBCollection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration("enrollment-repository-configuration")
class RepositoryConfig {

    @Bean(name = "enrollment-events")
    def DBCollection enrollmentEventsCollection(DB db) {
        db.enrollmentEventsCollection
    }

    @Bean(name = "enrollment-participants")
    def DBCollection enrollmentParticipantsCollection(DB db) {
        db.enrollmentParticipantsCollection
    }
}

package knbit.events.bc.readmodel.eventproposal

import com.mongodb.DB
import com.mongodb.DBCollection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration('event-proposal-repository-configuration')
class RepositoryConfig {

    @Bean(name = 'proposal-events')
    def DBCollection surveyEventsCollection(DB db) {
        db.eventsProposalCollection
    }

}

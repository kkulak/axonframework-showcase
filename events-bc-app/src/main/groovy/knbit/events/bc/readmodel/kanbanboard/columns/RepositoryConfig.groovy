package knbit.events.bc.readmodel.kanbanboard.columns

import com.mongodb.DB
import com.mongodb.DBCollection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration('kanban-board-repository-configuration')
class RepositoryConfig {

    @Bean(name = 'kanban-board')
    def DBCollection surveyEventsCollection(DB db) {
        db.kanbanBoardCollection
    }

}

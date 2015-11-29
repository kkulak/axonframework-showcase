package knbit.events.bc.readmodel.kanbanboard.backlog

import com.mongodb.DB
import com.mongodb.DBCollection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by novy on 28.11.15.
 */
@Configuration("backlog-readmodel")
class MongoDBConfig {

    @Bean(name = "backlogReadmodel")
    def DBCollection backlogCollection(DB db) {
        db.backlogCollection
    }
}

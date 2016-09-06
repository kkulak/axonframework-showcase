package knbit.events.bc.readmodel.kanbanboard.eventready

import com.mongodb.DB
import com.mongodb.DBCollection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration("readyevent-readmodel")
class MongoDBConfig {

    @Bean(name = "readyevent")
    def DBCollection readyEventCollection(DB db) {
        db.readyEventCollection
    }

}

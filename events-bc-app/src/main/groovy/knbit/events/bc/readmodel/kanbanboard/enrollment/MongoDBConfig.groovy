package knbit.events.bc.readmodel.kanbanboard.enrollment

import com.mongodb.DB
import com.mongodb.DBCollection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by novy on 05.10.15.
 */

@Configuration("enrollment-readmodel")
class MongoDBConfig {

    @Bean(name = "enrollment")
    def DBCollection enrollmentCollection(DB db) {
        db.enrollmentCollection
    }
}

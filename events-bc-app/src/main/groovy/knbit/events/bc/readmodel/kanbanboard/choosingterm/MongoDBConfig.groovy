package knbit.events.bc.readmodel.kanbanboard.choosingterm

import com.mongodb.DB
import com.mongodb.DBCollection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by novy on 13.09.15.
 */

@Configuration("terms-readmodel")
class MongoDBConfig {

    @Bean(name = "choosing-term")
    def DBCollection termsCollection(DB db) {
        db.termsCollection
    }
}

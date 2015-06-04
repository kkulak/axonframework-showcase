package knbit.events.bc.interest.viewmodel.eventmaster

import com.foursquare.fongo.Fongo
import com.gmongo.GMongo
import com.mongodb.DB
import com.mongodb.DBCollection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by novy on 04.06.15.
 */

@Configuration
class MongoDBConfig {

    @Bean
    def GMongo gMongo() {
        def mongoClient = new Fongo("survey-event-master-view-model").getMongo()
        new GMongo(mongoClient)
    }

    @Bean
    def DB db(GMongo mongo) {
        mongo.getDB("survey-event-master-view-model")
    }

    @Bean
    def DBCollection collection(DB db) {
        db.surveyEventMasterViewModel
    }
}

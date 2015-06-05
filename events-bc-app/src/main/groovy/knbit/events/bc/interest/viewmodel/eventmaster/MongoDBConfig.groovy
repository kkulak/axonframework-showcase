package knbit.events.bc.interest.viewmodel.eventmaster

import com.github.fakemongo.Fongo
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
        def mongoClient = new Fongo("view-model").getMongo()
        new GMongo(mongoClient)
    }

    @Bean
    def DB db(GMongo mongo) {
        mongo.getDB("view-model")
    }

    @Bean(name = "survey")
    def DBCollection surveyCollection(DB db) {
        db.surveyCollection
    }

    @Bean(name = "questionnaire")
    def DBCollection questionnaireCollection(DB db) {
        db.questionnaireCollection
    }

}

package knbit.events.bc.readmodel

import com.github.fakemongo.Fongo
import com.gmongo.GMongo
import com.mongodb.DB
import com.mongodb.MongoClient
import knbit.events.bc.common.Profiles
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

/**
 * Created by novy on 27.09.15.
 */

@Configuration("view-model-mongodb")
class MongoDBConfig {

    @Profile(Profiles.DEV)
    @Bean
    def GMongo devGMongo() {
        def mongoClient = new Fongo("view-model").getMongo()
        new GMongo(mongoClient)
    }

    @Profile(Profiles.PROD)
    @Bean
    def GMongo prodGMongo() {
        def mongoClient = new MongoClient("eventsbcmongo")
        new GMongo(mongoClient)
    }

    @Bean
    def DB db(GMongo mongo) {
        mongo.getDB("view-model")
    }
}

package knbit.events.bc.readmodel.kanbanboard.interest

import com.mongodb.DB
import com.mongodb.DBCollection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by novy on 04.06.15.
 */

@Configuration("survey-readmodel")
class MongoDBConfig {

    @Bean(name = "survey")
    def DBCollection surveyCollection(DB db) {
        db.surveyCollection
    }

    @Bean(name = "questionnaire")
    def DBCollection questionnaireCollection(DB db) {
        db.questionnaireCollection
    }
}

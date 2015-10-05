package knbit.events.bc.readmodel

import com.github.fakemongo.Fongo
import com.gmongo.GMongo
import com.mongodb.DBCollection

/**
 * Created by novy on 05.10.15.
 */
trait DBCollectionAware {

    def DBCollection testCollection() {
        collectionFor("test-collection")
    }

    def DBCollection testCollectionWithName(String collectionName) {
        collectionFor(collectionName)
    }

    private def collectionFor(String collectionName) {
        def GMongo gMongo = new GMongo(
                new Fongo("test-fongo").getMongo()
        )
        def db = gMongo.getDB("test-db")

        db.getCollection(collectionName)
    }
}
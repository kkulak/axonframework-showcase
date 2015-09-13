package knbit.events.bc.readmodel.kanbanboard.choosingterm.handlers

import com.mongodb.DBCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

/**
 * Created by novy on 13.09.15.
 */

@Component
class CreatingUnderChoosingTermEventHandler {

    def DBCollection termsCollection

    @Autowired
    CreatingUnderChoosingTermEventHandler(@Qualifier("choosing-term") DBCollection termsCollection) {
        this.termsCollection = termsCollection
    }
}

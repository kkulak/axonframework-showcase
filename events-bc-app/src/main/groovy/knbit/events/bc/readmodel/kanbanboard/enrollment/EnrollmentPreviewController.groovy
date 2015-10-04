package knbit.events.bc.readmodel.kanbanboard.enrollment

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.enums.EventFrequency
import knbit.events.bc.common.domain.enums.EventType
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by novy on 05.10.15.
 */

@RestController
@RequestMapping("/events/")
class EnrollmentPreviewController {

    def DBCollection enrollmentCollection

    @Autowired
    EnrollmentPreviewController(@Qualifier("enrollment") DBCollection enrollmentCollection) {
        this.enrollmentCollection = enrollmentCollection
    }

    @RequestMapping(value = "/{eventId}/enrollment", method = RequestMethod.GET)
    def enrollmentPreviewFor(@PathVariable("eventId") eventId) {
        [
                domainId      : "eventId",
                name          : "name",
                description   : "desc",
                eventType     : EventType.WORKSHOP,
                eventFrequency: EventFrequency.ONE_OFF,

                terms: [
                        [
                                termId           : 'termId',
                                date             : DateTime.now(),
                                duration         : 90,
                                capacity         : 100,
                                location         : '3.21A',
                                lecturer         : [
                                        firstName: 'firstname',
                                        lastName : 'lastname'
                                ],
                                participantsLimit: 30
                        ]

                ]
        ]

    }

}
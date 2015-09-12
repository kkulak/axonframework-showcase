package knbit.events.bc.readmodel.kanbanboard.choosingterm

import org.joda.time.DateTime
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by novy on 01.09.15.
 */

@RestController
@RequestMapping("/events/")
class ChoosingTermPreviewController {

    @RequestMapping(value = "/{eventId}/choosing-term", method = RequestMethod.GET)
    def termsPreviewFor(@PathVariable("eventId") eventId) {

        [
                domainId      : eventId,
                name          : "what a fancy event!",
                description   : "description",
                eventType     : "LECTURE",
                eventFrequency: "ONE_OFF",
                terms         : [
                        [
                                date    : DateTime.now(),
                                duration: 90,
                                capacity: 666,
                                location: "3.27A"
                        ]
                ],
                reservations  : [
                        [
                                date    : DateTime.now(),
                                duration: 60,
                                capacity: 100
                        ]

                ]
        ]
    }

}

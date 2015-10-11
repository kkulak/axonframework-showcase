package knbit.events.bc.readmodel

import knbit.events.bc.common.domain.valueobjects.EventDetails

/**
 * Created by novy on 11.10.15.
 */

class EventDetailsWrapper {

    static def asMap(EventDetails eventDetails) {
        [
                name          : eventDetails.name().value(),
                description   : eventDetails.description().value(),
                eventType     : eventDetails.type(),
                eventFrequency: eventDetails.frequency()
        ]
    }
}

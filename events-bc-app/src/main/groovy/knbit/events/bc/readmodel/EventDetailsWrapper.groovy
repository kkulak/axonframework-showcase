package knbit.events.bc.readmodel

import knbit.events.bc.common.domain.valueobjects.EventDetails
import knbit.events.bc.common.domain.valueobjects.Section
import knbit.events.bc.common.domain.valueobjects.URL

/**
 * Created by novy on 11.10.15.
 */

class EventDetailsWrapper {

    static def asMap(EventDetails eventDetails) {
        [
                name          : eventDetails.name().value(),
                description   : eventDetails.description().value(),
                eventType     : eventDetails.type(),
                imageUrl      : urlOrNull(eventDetails.imageUrl()),
                section       : sectionOrNull(eventDetails.section())
        ]
    }

    static def urlOrNull(Optional<URL> url) {
        url.map { it.value() }.orElse(null)
    }

    static def sectionOrNull(Optional<Section> section) {
        section.map {
            s -> [
                id  : s.id(),
                name: s.name()
            ]
        }.orElse(null)
    }

}

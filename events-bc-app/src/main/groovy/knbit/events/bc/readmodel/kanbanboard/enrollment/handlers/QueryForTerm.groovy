package knbit.events.bc.readmodel.kanbanboard.enrollment.handlers

import knbit.events.bc.choosingterm.domain.valuobjects.TermId
import knbit.events.bc.common.domain.valueobjects.EventId

/**
 * Created by novy on 05.10.15.
 */
trait QueryForTerm {

    def queryFor(EventId eventId, TermId termId) {
        [
                eventId       : eventId.value(),
                'terms.termId': termId.value()
        ]
    }
}
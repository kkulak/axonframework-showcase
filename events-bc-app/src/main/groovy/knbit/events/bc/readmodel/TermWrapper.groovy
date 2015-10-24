package knbit.events.bc.readmodel

import knbit.events.bc.choosingterm.domain.valuobjects.IdentifiedTerm
import knbit.events.bc.choosingterm.domain.valuobjects.Term
import knbit.events.bc.choosingterm.domain.valuobjects.TermId

/**
 * Created by novy on 11.10.15.
 */
class TermWrapper {

    static asMap(IdentifiedTerm term) {
        [
                termId  : term.termId().value(),
                date    : term.duration().start(),
                duration: term.duration().duration().getStandardMinutes(),
                capacity: term.capacity().value(),
                location: term.location().value()
        ]
    }

    static asMap(TermId termId, Term term) {
        asMap(IdentifiedTerm.of(termId, term))
    }
}
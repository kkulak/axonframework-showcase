package knbit.events.bc.readmodel

import knbit.events.bc.choosingterm.domain.valuobjects.EnrollmentIdentifiedTerm
import knbit.events.bc.choosingterm.domain.valuobjects.Term
import knbit.events.bc.choosingterm.domain.valuobjects.TermId
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails

/**
 * Created by novy on 11.10.15.
 */
class TermWrapper {

    static asMap(EnrollmentIdentifiedTerm term) {
        [
                termId              : term.termId().value(),
                date                : term.duration().start(),
                duration            : term.duration().duration().getStandardMinutes(),
                limit               : term.capacity().value(),
                location            : term.location().value(),
                participantsLimit   : term.participantsLimit().value(),
                lecturers           : lecturersOf(term.lecturers())
        ]
    }

    static asMap(EventReadyDetails details) {
        [
                date    : details.duration().start(),
                duration: details.duration().duration().getStandardMinutes(),
                limit   : details.limit().value(),
                location: details.location().value()
        ]
    }

    static asMap(TermId id, Term term) {
        [
                termId              : id.value(),
                date                : term.duration().start(),
                duration            : term.duration().duration().getStandardMinutes(),
                limit               : term.capacity().value(),
                location            : term.location().value(),
        ]
    }

    static lecturersOf(Collection<Lecturer> lecturers) {
        lecturers.collect {[
                name : it.name(),
                id   : it.id().orElse(null)
        ]}
    }

}

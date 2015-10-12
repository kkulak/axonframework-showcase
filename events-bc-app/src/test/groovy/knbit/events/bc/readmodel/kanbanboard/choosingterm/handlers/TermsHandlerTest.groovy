package knbit.events.bc.readmodel.kanbanboard.choosingterm.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.*
import knbit.events.bc.choosingterm.domain.valuobjects.events.TermEvents
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.readmodel.DBCollectionAware
import org.joda.time.DateTime
import org.joda.time.Duration
import spock.lang.Specification

/**
 * Created by novy on 13.09.15.
 */
class TermsHandlerTest extends Specification implements DBCollectionAware {

    def TermsHandler objectUnderTest
    def DBCollection collection

    def EventId eventId
    def Term term
    def TermId termId

    void setup() {
        collection = testCollection()
        objectUnderTest = new TermsHandler(collection)
        eventId = EventId.of("eventId")
        term = Term.of(
                EventDuration.of(DateTime.now(), Duration.standardMinutes(90)),
                Capacity.of(15),
                Location.of('3.27 A')
        )
        termId = TermId.of("termId")

    }

    def "should add new term based on given event"() {
        given:
        collection << [
                eventId: eventId.value(),
                terms  : []
        ]

        when:
        objectUnderTest.on(
                TermEvents.TermAdded.of(eventId, termId, term)
        )

        then:
        def choosingTermsPreview = collection.findOne(
                eventId: eventId.value()
        )

        choosingTermsPreview.terms == [
                [
                        termId  : termId.value(),
                        date    : term.duration().start(),
                        duration: 90,
                        capacity: 15,
                        location: '3.27 A'
                ]
        ]
    }

    def "should remove given term on proper event"() {
        given:
        def termData = [
                termId  : termId.value(),
                date    : term.duration().start(),
                duration: 90,
                capacity: 15,
                location: '3.27 A'
        ]
        collection << [
                eventId: eventId.value(),
                terms  : [termData]
        ]

        when:
        objectUnderTest.on(
                TermEvents.TermRemoved.of(eventId, termId)
        )

        then:
        def choosingTermsPreview = collection.findOne(
                eventId: eventId.value()
        )

        choosingTermsPreview.terms == []
    }
}

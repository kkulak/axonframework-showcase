package knbit.events.bc.readmodel.kanbanboard.enrollment.handlers.enrollment

import com.github.fakemongo.Fongo
import com.gmongo.GMongo
import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.TermId
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.enrollment.domain.valueobjects.events.EnrollmentEvents
import spock.lang.Specification

/**
 * Created by novy on 05.10.15.
 */
class EnrollmentHandlerTest extends Specification {

    def EnrollmentHandler objectUnderTest
    def DBCollection collection

    def EventId eventId

    void setup() {
        def GMongo gMongo = new GMongo(
                new Fongo("test-fongo").getMongo()
        )
        def db = gMongo.getDB("test-db")
        collection = db.getCollection("test-collection")

        objectUnderTest = new EnrollmentHandler(collection)
        eventId = EventId.of("eventId")
    }

    def "should remove participant when needed"() {
        given:
        def termId = TermId.of("termId")
        def participantId = MemberId.of("memberId")
        collection << [
                domainId: eventId.value(),
                terms   : [
                        [
                                termId      : termId.value(),
                                participants: [
                                        [participantId: 'yet another participant'],
                                        [participantId: participantId.value()]
                                ]
                        ]
                ]
        ]

        when:
        objectUnderTest.on EnrollmentEvents.ParticipantDisenrolledFromTerm.of(eventId, termId, participantId)

        then:
        def enrollmentPreview = collection.findOne(domainId: eventId.value())
        enrollmentPreview.terms == [
                [
                        termId      : termId.value(),
                        participants: [
                                [participantId: 'yet another participant'],
                        ]
                ]
        ]
    }
}

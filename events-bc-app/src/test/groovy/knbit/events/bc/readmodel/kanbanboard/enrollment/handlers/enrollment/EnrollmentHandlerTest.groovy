package knbit.events.bc.readmodel.kanbanboard.enrollment.handlers.enrollment

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.TermId
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.enrollment.domain.valueobjects.events.EnrollmentEvents
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

/**
 * Created by novy on 05.10.15.
 */
class EnrollmentHandlerTest extends Specification implements DBCollectionAware {

    def EnrollmentHandler objectUnderTest
    def DBCollection collection
    def ParticipantDetailsRepository detailsRepositoryMock

    def EventId eventId
    def TermId termId

    void setup() {
        collection = testCollection()
        detailsRepositoryMock = Mock(ParticipantDetailsRepository)
        objectUnderTest = new EnrollmentHandler(collection, detailsRepositoryMock)
        eventId = EventId.of("eventId")
        termId = TermId.of("termId")
    }

    def "should remove participant when needed"() {
        given:
        def participantId = MemberId.of("memberId")
        collection << [
                eventId: eventId.value(),
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
        def enrollmentPreview = collection.findOne(eventId: eventId.value())
        enrollmentPreview.terms == [
                [
                        termId      : termId.value(),
                        participants: [
                                [participantId: 'yet another participant'],
                        ]
                ]
        ]
    }

    def "given participant enrolled event with given participantId, it should update database with participant details"() {
        given:
        def memberId = MemberId.of("memberId")
        collection << [
                eventId: eventId.value(),
                terms   : [
                        [termId: termId.value(), participants: []]
                ]
        ]
        def participantDetails = [
                participantId: memberId.value(),
                firstName    : 'firstName',
                lastName     : 'lastName'
        ]
        detailsRepositoryMock.detailsFor(memberId) >> participantDetails

        when:
        objectUnderTest.on EnrollmentEvents.ParticipantEnrolledForTerm.of(eventId, termId, memberId)

        then:
        def enrollmentPreview = collection.findOne(eventId: eventId.value())
        enrollmentPreview.terms == [
                [
                        termId      : termId.value(),
                        participants: [participantDetails]
                ]
        ]
    }
}

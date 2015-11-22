package knbit.events.bc.readmodel.members.enrollment.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.TermId
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.MemberId
import knbit.events.bc.enrollment.domain.valueobjects.events.EnrollmentEvents
import knbit.events.bc.enrollment.domain.valueobjects.events.EventUnderEnrollmentEvents
import knbit.events.bc.interest.builders.EventDetailsBuilder
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

/**
 * Created by novy on 11.10.15.
 */
class EnrollmentMemberHandlerTest extends Specification implements DBCollectionAware {

    def DBCollection enrollmentEventsCollection
    def DBCollection enrollmentParticipantsCollection

    def EnrollmentMemberHandler objectUnderTest

    def EventId eventId
    def TermId termId
    def MemberId memberId

    void setup() {
        enrollmentEventsCollection = testCollectionWithName('events')
        enrollmentParticipantsCollection = testCollectionWithName('participants')

        objectUnderTest = new EnrollmentMemberHandler(enrollmentEventsCollection, enrollmentParticipantsCollection)

        eventId = EventId.of("eventId")
        termId = TermId.of("termId")
        memberId = MemberId.of("memberId")
    }

    def "it should increase participant count on participant enrolled event"() {
        given:
        enrollmentEventsCollection << [
                eventId: eventId.value(),
                terms  : [
                        [termId: termId.value(), participantsEnrolled: 0],
                        [termId: 'another term', participantsEnrolled: 666]
                ]
        ]

        when:
        objectUnderTest.on EnrollmentEvents.ParticipantEnrolledForTerm.of(eventId, termId, new MemberId())

        then:
        def eventData = enrollmentEventsCollection.findOne([eventId: eventId.value()])
        eventData.terms == [
                [termId: termId.value(), participantsEnrolled: 1],
                [termId: 'another term', participantsEnrolled: 666]
        ]
    }

    def "should save member preferences on participant enrolled event"() {
        when:
        objectUnderTest.on EnrollmentEvents.ParticipantEnrolledForTerm.of(eventId, termId, memberId)

        then:
        def collectionEntry = enrollmentParticipantsCollection.findOne(
                [eventId: eventId.value(), memberId: memberId.value()]
        )
        stripMongoIdFrom(collectionEntry) == [
                eventId : eventId.value(),
                memberId: memberId.value(),
                termId  : termId.value()
        ]
    }

    def "should decrease participant count on participant disenrolled event"() {
        given:
        enrollmentEventsCollection << [
                eventId: eventId.value(),
                terms  : [
                        [termId: termId.value(), participantsEnrolled: 1],
                        [termId: 'another term', participantsEnrolled: 666]
                ]
        ]

        when:
        objectUnderTest.on EnrollmentEvents.ParticipantDisenrolledFromTerm.of(eventId, termId, new MemberId())

        then:
        def eventData = enrollmentEventsCollection.findOne([eventId: eventId.value()])
        eventData.terms == [
                [termId: termId.value(), participantsEnrolled: 0],
                [termId: 'another term', participantsEnrolled: 666]
        ]
    }

    def "should remove member preferences on participant disenrolled event"() {
        given:
        enrollmentParticipantsCollection << [
                [eventId: eventId.value(), memberId: memberId.value(), termId: termId.value()],
                [eventId: eventId.value(), memberId: 'another member', termId: termId.value()],
        ]

        when:
        objectUnderTest.on EnrollmentEvents.ParticipantDisenrolledFromTerm.of(eventId, termId, memberId)

        then:
        def participantsPreferences = enrollmentParticipantsCollection.find().toArray()
        participantsPreferences.collect { stripMongoIdFrom(it) } == [
                [eventId: eventId.value(), memberId: 'another member', termId: termId.value()]
        ]
    }

    def "should remove all member preferences related to event on that event transition"() {
        given:
        enrollmentParticipantsCollection << [
                [eventId: eventId.value(), memberId: memberId.value(), termId: termId.value()],
                [eventId: eventId.value(), memberId: 'another member', termId: termId.value()],
        ]

        when:
        objectUnderTest.on EventUnderEnrollmentEvents.TransitedToReady.of(
                eventId,
                EventDetailsBuilder.defaultEventDetails(),
                []
        )

        then:
        enrollmentParticipantsCollection.find().toArray() == []
    }

    def "should remove all member preferences related to event on that event cancellation"() {
        given:
        enrollmentParticipantsCollection << [
                [eventId: eventId.value(), memberId: memberId.value(), termId: termId.value()],
                [eventId: eventId.value(), memberId: 'another member', termId: termId.value()],
        ]

        when:
        objectUnderTest.on EventUnderEnrollmentEvents.Cancelled.of(eventId, [])

        then:
        enrollmentParticipantsCollection.find().toArray() == []
    }
}

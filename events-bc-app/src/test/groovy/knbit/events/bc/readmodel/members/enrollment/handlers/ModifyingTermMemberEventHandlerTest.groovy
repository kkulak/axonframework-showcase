package knbit.events.bc.readmodel.members.enrollment.handlers

import com.mongodb.DBCollection
import knbit.events.bc.choosingterm.domain.valuobjects.TermId
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit
import knbit.events.bc.enrollment.domain.valueobjects.events.TermModifyingEvents
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

/**
 * Created by novy on 05.10.15.
 */

//TODO COPY & PASTED, REFACTOR LATER!!!!!!!
class ModifyingTermMemberEventHandlerTest extends Specification implements DBCollectionAware {

    def DBCollection collection
    def ModifyingTermMemberEventHandler objectUnderTest

    def EventId eventId
    def TermId termId

    void setup() {
        collection = testCollection()
        objectUnderTest = new ModifyingTermMemberEventHandler(collection)
        eventId = EventId.of("eventId")
        termId = TermId.of("termId")
    }

    def "should update participantsLimit when needed"() {
        given:
        collection << [
                eventId: eventId.value(),
                terms  : [
                        [termId: termId.value()],
                        [termId: "anotherId"]
                ]
        ]
        def participantsLimit = ParticipantsLimit.of(66)

        when:
        objectUnderTest.on TermModifyingEvents.ParticipantLimitSet.of(eventId, termId, participantsLimit)

        then:
        def eventPreview = collection.findOne(eventId: eventId.value())
        eventPreview.terms == [
                [
                        termId           : termId.value(),
                        participantsLimit: participantsLimit.value()
                ],
                [termId: "anotherId"]
        ]
    }

    def "should update lecturer when needed"() {
        given:
        collection << [
                eventId: eventId.value(),
                terms  : [
                        [termId: termId.value()],
                        [termId: "anotherId"]
                ]
        ]
        def lecturer = Lecturer.of("firstName", "lastName")

        when:
        objectUnderTest.on TermModifyingEvents.LecturerAssigned.of(eventId, termId, lecturer)

        then:
        def eventPreview = collection.findOne(eventId: eventId.value())
        eventPreview.terms == [
                [
                        termId  : termId.value(),
                        lecturer: [
                                firstName: lecturer.firstName(),
                                lastName : lecturer.lastName()
                        ]
                ],
                [termId: "anotherId"]
        ]
    }
}
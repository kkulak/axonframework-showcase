package knbit.events.bc.readmodel.members.surveypreview.handlers

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.interest.builders.EventDetailsBuilder
import knbit.events.bc.interest.domain.enums.AnswerType
import knbit.events.bc.interest.domain.policies.completingquestionnaire.SingleChoiceAnswerPolicy
import knbit.events.bc.interest.domain.policies.completingquestionnaire.TextChoiceAnswerPolicy
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEvents
import knbit.events.bc.interest.domain.valueobjects.events.QuestionnaireEvents
import knbit.events.bc.interest.domain.valueobjects.question.Question
import knbit.events.bc.interest.domain.valueobjects.question.QuestionDescription
import knbit.events.bc.interest.domain.valueobjects.question.QuestionTitle
import knbit.events.bc.interest.domain.valueobjects.question.answer.DomainAnswer
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

import static knbit.events.bc.readmodel.EventDetailsWrapper.sectionOrNull
import static knbit.events.bc.readmodel.EventDetailsWrapper.urlOrNull

/**
 * Created by novy on 10.10.15.
 */
class MemberSurveyEventHandlerTest extends Specification implements DBCollectionAware {

    def DBCollection collection

    def MemberSurveyEventHandler objectUnderTest

    def EventId eventId

    void setup() {
        collection = testCollection()
        objectUnderTest = new MemberSurveyEventHandler(collection)

        eventId = EventId.of("eventId")
    }

    def "should create collection entry on event creation"() {
        when:
        def eventDetails = EventDetailsBuilder.defaultEventDetails()
        objectUnderTest.on InterestAwareEvents.Created.of(eventId, eventDetails)

        then:
        def collectionEntry = collection.findOne(eventId: eventId.value())
        stripMongoIdFrom(collectionEntry) == [
                eventId    : eventId.value(),
                name       : eventDetails.name().value(),
                description: eventDetails.description().value(),
                eventType  : eventDetails.type(),
                imageUrl   : urlOrNull(eventDetails.imageUrl()),
                section    : sectionOrNull(eventDetails.section()),
                votedUp    : 0
        ]
    }

    def "should assign questionnaire"() {
        given:
        collection << [eventId: eventId.value()]

        def questions = [
                Question.of(
                        QuestionTitle.of("q1"),
                        QuestionDescription.of("q1 desc"),
                        new SingleChoiceAnswerPolicy([DomainAnswer.of("ans1"), DomainAnswer.of("ans2")])
                ),
                Question.of(
                        QuestionTitle.of("q2"),
                        QuestionDescription.of("q2 desc"),
                        new TextChoiceAnswerPolicy()
                ),
        ]

        when:
        objectUnderTest.on QuestionnaireEvents.Added.of(eventId, questions)

        then:
        def collectionEntry = collection.findOne(eventId: eventId.value())
        stripMongoIdFrom(collectionEntry) == [
                eventId  : eventId.value(),
                questions: [
                        [
                                title      : "q1",
                                description: "q1 desc",
                                type       : AnswerType.SINGLE_CHOICE,
                                answers    : ["ans1", "ans2"]
                        ],
                        [
                                title      : "q2",
                                description: "q2 desc",
                                type       : AnswerType.TEXT,
                                answers    : []
                        ]
                ]
        ]
    }

    def "should delete collection entry on transition"() {
        given:
        collection << [
                [eventId: eventId.value()],
                [eventId: 'anotherId'],
                [eventId: 'andYetAnother']
        ]

        when:
        objectUnderTest.on InterestAwareEvents.TransitedToUnderChoosingTerm.of(
                eventId, EventDetailsBuilder.defaultEventDetails()
        )

        then:
        def allEvents = collection.find().toArray()
        allEvents.collect { stripMongoIdFrom(it) } == [
                [eventId: 'anotherId'],
                [eventId: 'andYetAnother']
        ]
    }

    def "should delete collection entry on event cancellation"() {
        given:
        collection << [eventId: eventId.value()]

        when:
        objectUnderTest.on(
                [eventId: { return eventId }] as InterestAwareEvents.InterestAwareEventCancelled
        )

        then:
        collection.find([eventId: eventId.value()]).toArray() == []
    }
}

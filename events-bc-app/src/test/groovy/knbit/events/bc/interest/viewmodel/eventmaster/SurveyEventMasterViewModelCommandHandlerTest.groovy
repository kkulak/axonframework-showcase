package knbit.events.bc.interest.viewmodel.eventmaster

import com.foursquare.fongo.Fongo
import com.gmongo.GMongo
import knbit.events.bc.common.domain.enums.EventFrequency
import knbit.events.bc.common.domain.enums.EventType
import knbit.events.bc.common.domain.valueobjects.Description
import knbit.events.bc.common.domain.valueobjects.EventDetails
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.common.domain.valueobjects.Name
import knbit.events.bc.interest.domain.enums.AnswerType
import knbit.events.bc.interest.domain.policies.completingquestionnaire.MultipleChoiceAnswerPolicy
import knbit.events.bc.interest.domain.policies.completingquestionnaire.SingleChoiceAnswerPolicy
import knbit.events.bc.interest.domain.policies.completingquestionnaire.TextChoiceAnswerPolicy
import knbit.events.bc.interest.domain.valueobjects.events.InterestAwareEventCreated
import knbit.events.bc.interest.domain.valueobjects.events.QuestionnaireAddedEvent
import knbit.events.bc.interest.domain.valueobjects.question.Question
import knbit.events.bc.interest.domain.valueobjects.question.QuestionDescription
import knbit.events.bc.interest.domain.valueobjects.question.QuestionTitle
import knbit.events.bc.interest.domain.valueobjects.question.answer.DomainAnswer
import spock.lang.Specification

/**
 * Created by novy on 04.06.15.
 */
class SurveyEventMasterViewModelCommandHandlerTest extends Specification {

    def SurveyEventMasterViewModelCommandHandler objectUnderTest
    def SurveyEventMasterViewModelResource repository

    def EventId eventId
    def EventDetails eventDetails

    void setup() {

        def GMongo gMongo = new GMongo(
                new Fongo("test-db").getMongo()
        )
        repository = new SurveyEventMasterViewModelResource(gMongo.getDB("test-db"))
        objectUnderTest = new SurveyEventMasterViewModelCommandHandler(repository)
        eventId = EventId.of("eventId")
        eventDetails = EventDetails.of(
                Name.of("name"),
                Description.of("desc"),
                EventType.WORKSHOP,
                EventFrequency.ONE_OFF
        )
    }

    def "should create database entry containing only event details on InterestAwareEventCreated event"() {

        given:
        def interestAwareEventCreated = new InterestAwareEventCreated(eventId, eventDetails)

        when:
        objectUnderTest.on interestAwareEventCreated

        then:
        def interestAwareEventViewModel = repository.loadByEventDomainId(
                eventId.value()
        )

        interestAwareEventViewModel["domainId"] == "eventId"
        interestAwareEventViewModel["name"] == "name"
        interestAwareEventViewModel["description"] == "desc"
        interestAwareEventViewModel["eventType"] == EventType.WORKSHOP
        interestAwareEventViewModel["eventFrequency"] == EventFrequency.ONE_OFF
    }

    def "adding questionnaire with multiple choice question should result in 0 votes stored for each answer"() {

        given:
        objectUnderTest.on new InterestAwareEventCreated(eventId, eventDetails)

        when:
        objectUnderTest.on(QuestionnaireAddedEvent.of(
                eventId, [
                Question.of(
                        QuestionTitle.of("title"),
                        QuestionDescription.of("desc"),
                        new MultipleChoiceAnswerPolicy([
                                DomainAnswer.of("ans1"),
                                DomainAnswer.of("ans2")
                        ])
                )
        ]))

        def interestAwareEventViewModel = repository.loadByEventDomainId(
                eventId.value()
        )

        then:
        interestAwareEventViewModel["questions"] == [
                [
                        title       : "title",
                        description : "desc",
                        questionType: AnswerType.MULTIPLE_CHOICE,
                        answers     : [
                                [
                                        value   : "ans1",
                                        answered: 0
                                ],
                                [
                                        value   : "ans2",
                                        answered: 0
                                ]
                        ]
                ]
        ]
    }

    def "adding questionnaire with single choice question should result in 0 votes stored for each answer"() {

        given:
        objectUnderTest.on new InterestAwareEventCreated(eventId, eventDetails)

        when:
        objectUnderTest.on(QuestionnaireAddedEvent.of(
                eventId, [
                Question.of(
                        QuestionTitle.of("title"),
                        QuestionDescription.of("desc"),
                        new SingleChoiceAnswerPolicy([
                                DomainAnswer.of("ans1"),
                                DomainAnswer.of("ans2")
                        ])
                )
        ]))

        def interestAwareEventViewModel = repository.loadByEventDomainId(
                eventId.value()
        )

        then:
        interestAwareEventViewModel["questions"] == [
                [
                        title       : "title",
                        description : "desc",
                        questionType: AnswerType.SINGLE_CHOICE,
                        answers     : [
                                [
                                        value   : "ans1",
                                        answered: 0
                                ],
                                [
                                        value   : "ans2",
                                        answered: 0
                                ]
                        ]
                ]
        ]
    }

    def "adding questionnaire with text question should result in question with no answers"() {

        given:
        objectUnderTest.on new InterestAwareEventCreated(eventId, eventDetails)

        when:
        objectUnderTest.on(QuestionnaireAddedEvent.of(
                eventId, [
                Question.of(
                        QuestionTitle.of("title"),
                        QuestionDescription.of("desc"),
                        new TextChoiceAnswerPolicy()
                )
        ]))

        def interestAwareEventViewModel = repository.loadByEventDomainId(
                eventId.value()
        )

        then:
        interestAwareEventViewModel["questions"] == [
                [
                        title       : "title",
                        description : "desc",
                        questionType: AnswerType.TEXT,
                        answers     : []
                ]
        ]
    }
}

package knbit.events.bc.readmodel.kanbanboard.interest


import knbit.events.bc.common.domain.enums.EventType
import knbit.events.bc.interest.domain.enums.AnswerType
import knbit.events.bc.readmodel.DBCollectionAware
import spock.lang.Specification

/**
 * Created by novy on 05.06.15.
 */
class InterestPreviewRepositoryTest extends Specification implements DBCollectionAware {

    def surveyCollection
    def questionnaireCollection

    def InterestPreviewRepository objectUnderTest

    void setup() {
        surveyCollection = testCollectionWithName("survey-collection")
        questionnaireCollection = testCollectionWithName("questionnaire-collection")

        objectUnderTest = new InterestPreviewRepository(
                surveyCollection, questionnaireCollection
        )
    }

    def "should merge survey and questionnaire, preserving question order"() {

        given:
        surveyCollection << [
                [
                        _id           : 'anId',
                        eventId       : "eventId",
                        name          : "name",
                        description   : "desc",
                        eventType     : EventType.WORKSHOP,
                        votedDown     : 15,
                        votedUp       : 17
                ],

                [
                        eventId: "dummyId"
                ]
        ]

        questionnaireCollection << [

                [
                        _id           : 'questionId2',
                        eventId       : "eventId",
                        questionNumber: 2,
                        title         : "title",
                        description   : "desc",
                        questionType  : AnswerType.TEXT,
                        answers       : ["ans1", "ans2"]
                ],

                [
                        _id           : 'questionId1',
                        eventId       : "eventId",
                        questionNumber: 1,
                        title         : "title",
                        description   : "desc",
                        questionType  : AnswerType.MULTIPLE_CHOICE,
                        answers       : [
                                [value: "ans1", answered: 0],
                                [value: "ans2", answered: 0],
                                [value: "ans3", answered: 0]
                        ]
                ]
        ]

        when:
        def mergedData = objectUnderTest.load("eventId")


        then:
        mergedData == [
                _id           : 'anId',
                eventId       : "eventId",
                name          : "name",
                description   : "desc",
                eventType     : EventType.WORKSHOP,
                votedDown     : 15,
                votedUp       : 17,
                questions     : [
                        [
                                _id           : 'questionId1',
                                eventId       : "eventId",
                                questionNumber: 1,
                                title         : "title",
                                description   : "desc",
                                questionType  : AnswerType.MULTIPLE_CHOICE,
                                answers       : [
                                        [value: "ans1", answered: 0],
                                        [value: "ans2", answered: 0],
                                        [value: "ans3", answered: 0]
                                ]
                        ],
                        [
                                _id           : 'questionId2',
                                eventId       : "eventId",
                                questionNumber: 2,
                                title         : "title",
                                description   : "desc",
                                questionType  : AnswerType.TEXT,
                                answers       : ["ans1", "ans2"]
                        ]
                ]

        ]
    }

    def "should not include empty question list if there is no questionnaire"() {

        given:
        surveyCollection << [
                [
                        _id           : 'anId',
                        eventId       : "eventId",
                        name          : "name",
                        description   : "desc",
                        eventType     : EventType.WORKSHOP,
                        votedDown     : 15,
                        votedUp       : 17
                ],

                [
                        eventId: "dummyId"
                ]
        ]

        when:
        def mergedData = objectUnderTest.load("eventId")

        then:
        mergedData == [
                _id           : 'anId',
                eventId       : "eventId",
                name          : "name",
                description   : "desc",
                eventType     : EventType.WORKSHOP,
                votedDown     : 15,
                votedUp       : 17

        ]
    }

    def "should throw an exception if there is no survey for given id"() {

        when:
        objectUnderTest.load('dummyId')

        then:
        thrown(IllegalArgumentException.class)

    }
}

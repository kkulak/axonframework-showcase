package knbit.events.bc.readmodel.kanbanboard.interest

import com.github.fakemongo.Fongo
import com.gmongo.GMongo
import knbit.events.bc.common.domain.enums.EventFrequency
import knbit.events.bc.common.domain.enums.EventType
import knbit.events.bc.interest.domain.enums.AnswerType
import knbit.events.bc.readmodel.kanbanboard.interest.InterestPreviewRepository
import spock.lang.Specification

/**
 * Created by novy on 05.06.15.
 */
class InterestPreviewRepositoryTest extends Specification {

    def surveyCollection
    def questionnaireCollection

    def InterestPreviewRepository objectUnderTest

    void setup() {

        def GMongo gMongo = new GMongo(
                new Fongo("test-fongo").getMongo()
        )
        def db = gMongo.getDB("test-db")

        surveyCollection = db.getCollection("survey-collection")
        questionnaireCollection = db.getCollection("questionnaire-collection")

        objectUnderTest = new InterestPreviewRepository(
                surveyCollection, questionnaireCollection
        )

    }

    def "should merge survey and questionnaire, preserving question order"() {

        given:
        surveyCollection << [
                [
                        _id           : 'anId',
                        domainId      : "eventId",
                        name          : "name",
                        description   : "desc",
                        eventType     : EventType.WORKSHOP,
                        eventFrequency: EventFrequency.ONE_OFF,
                        votedDown     : 15,
                        votedUp       : 17
                ],

                [
                        domainId: "dummyId"
                ]
        ]

        questionnaireCollection << [

                [
                        _id           : 'questionId2',
                        domainId      : "eventId",
                        questionNumber: 2,
                        title         : "title",
                        description   : "desc",
                        questionType  : AnswerType.TEXT,
                        answers       : ["ans1", "ans2"]
                ],

                [
                        _id           : 'questionId1',
                        domainId      : "eventId",
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
                domainId      : "eventId",
                name          : "name",
                description   : "desc",
                eventType     : EventType.WORKSHOP,
                eventFrequency: EventFrequency.ONE_OFF,
                votedDown     : 15,
                votedUp       : 17,
                questions     : [
                        [
                                _id           : 'questionId1',
                                domainId      : "eventId",
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
                                domainId      : "eventId",
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
                        domainId      : "eventId",
                        name          : "name",
                        description   : "desc",
                        eventType     : EventType.WORKSHOP,
                        eventFrequency: EventFrequency.ONE_OFF,
                        votedDown     : 15,
                        votedUp       : 17
                ],

                [
                        domainId: "dummyId"
                ]
        ]

        when:
        def mergedData = objectUnderTest.load("eventId")

        then:
        mergedData == [
                _id           : 'anId',
                domainId      : "eventId",
                name          : "name",
                description   : "desc",
                eventType     : EventType.WORKSHOP,
                eventFrequency: EventFrequency.ONE_OFF,
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

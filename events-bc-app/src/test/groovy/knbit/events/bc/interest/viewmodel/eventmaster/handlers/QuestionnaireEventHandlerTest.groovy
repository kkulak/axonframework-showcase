package knbit.events.bc.interest.viewmodel.eventmaster.handlers

import com.foursquare.fongo.Fongo
import com.gmongo.GMongo
import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.interest.domain.enums.AnswerType
import knbit.events.bc.interest.domain.policies.completingquestionnaire.MultipleChoiceAnswerPolicy
import knbit.events.bc.interest.domain.policies.completingquestionnaire.SingleChoiceAnswerPolicy
import knbit.events.bc.interest.domain.policies.completingquestionnaire.TextChoiceAnswerPolicy
import knbit.events.bc.interest.domain.valueobjects.events.QuestionnaireAddedEvent
import knbit.events.bc.interest.domain.valueobjects.question.Question
import knbit.events.bc.interest.domain.valueobjects.question.QuestionDescription
import knbit.events.bc.interest.domain.valueobjects.question.QuestionTitle
import knbit.events.bc.interest.domain.valueobjects.question.answer.DomainAnswer
import spock.lang.Specification

/**
 * Created by novy on 04.06.15.
 */
class QuestionnaireEventHandlerTest extends Specification {

    def QuestionnaireEventHandler objectUnderTest
    def DBCollection collection

    def EventId eventId

    void setup() {

        def GMongo gMongo = new GMongo(
                new Fongo("test-fongo").getMongo()
        )
        def db = gMongo.getDB("test-db")
        collection = db.getCollection("test-collection")

        objectUnderTest = new QuestionnaireEventHandler(collection)
        eventId = EventId.of("eventId")
    }

    def "adding questionnaire with multiple choice question should result in 0 votes stored for each answer"() {

        given:
        collection << [domainId: eventId.value()]

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

        then:
        def interestAwareEventViewModel = collection.findOne(
                domainId: eventId.value()
        )

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
        collection << [domainId: eventId.value()]

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

        then:
        def interestAwareEventViewModel = collection.findOne(
                domainId: eventId.value()
        )

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
        collection << [domainId: eventId.value()]

        when:
        objectUnderTest.on(QuestionnaireAddedEvent.of(
                eventId, [
                Question.of(
                        QuestionTitle.of("title"),
                        QuestionDescription.of("desc"),
                        new TextChoiceAnswerPolicy()
                )
        ]))

        then:
        def interestAwareEventViewModel = collection.findOne(
                domainId: eventId.value()
        )

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

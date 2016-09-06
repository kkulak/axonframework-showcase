package knbit.events.bc.interest.web

import knbit.events.bc.backlogevent.domain.valueobjects.commands.BacklogEventCommands
import knbit.events.bc.common.domain.valueobjects.EventId
import knbit.events.bc.interest.domain.enums.AnswerType
import knbit.events.bc.interest.domain.valueobjects.commands.QuestionnaireCommands
import knbit.events.bc.interest.domain.valueobjects.commands.SurveyCommands
import knbit.events.bc.interest.domain.valueobjects.question.QuestionData
import knbit.events.bc.interest.domain.valueobjects.question.QuestionDescription
import knbit.events.bc.interest.domain.valueobjects.question.QuestionTitle
import knbit.events.bc.interest.domain.valueobjects.question.answer.DomainAnswer
import knbit.events.bc.interest.web.forms.QuestionDataDTO
import knbit.events.bc.interest.web.forms.SurveyForm
import org.axonframework.commandhandling.gateway.CommandGateway
import org.joda.time.DateTime
import spock.lang.Specification

/**
 * Created by novy on 01.06.15.
 */
class InterestAwareEventControllerTest extends Specification {

    def CommandGateway commandGatewayMock

    void setup() {
        commandGatewayMock = Mock(CommandGateway.class)
    }

    def "should dispatch a command to change BacklogEvent's state"() {

        given:
        def objectUnderTest = new InterestAwareEventController(commandGatewayMock)
        def surveyForm = new SurveyForm()
        surveyForm.questions = []

        when:
        objectUnderTest.createSurvey("eventId", surveyForm)

        then:
        1 * commandGatewayMock.sendAndWait(
                BacklogEventCommands.TransitToInterestAwareEventCommand.of(EventId.of("eventId"))
        )
    }

    def "should transform dtos to domain objects and send proper command"() {

        given:
        def objectUnderTest = new InterestAwareEventController(commandGatewayMock)
        def surveyForm = new SurveyForm()
        surveyForm.questions = [
                new QuestionDataDTO(
                        "title",
                        "desc",
                        AnswerType.SINGLE_CHOICE,
                        ["opt1", "opt2"]
                )
        ]

        when:
        objectUnderTest.createSurvey("eventId", surveyForm)

        then:
        1 * commandGatewayMock.sendAndWait(
                QuestionnaireCommands.Add.of(
                        EventId.of("eventId"),
                        [
                                QuestionData.of(
                                        QuestionTitle.of("title"),
                                        QuestionDescription.of("desc"),
                                        AnswerType.SINGLE_CHOICE,
                                        [
                                                DomainAnswer.of("opt1"), DomainAnswer.of("opt2")
                                        ]
                                )
                        ]
                )
        )

    }

    def "should dispatch command to start surveying"() {

        given:
        def objectUnderTest = new InterestAwareEventController(commandGatewayMock)
        def desiredInterestThreshold = Optional.ofNullable(15)
        def desiredEndingDate = Optional.of(DateTime.now())

        def SurveyForm surveyForm = new SurveyForm()

        surveyForm.questions = []
        surveyForm.minimalInterestThreshold = desiredInterestThreshold
        surveyForm.endOfSurveying = desiredEndingDate

        when:
        objectUnderTest.createSurvey("eventId", surveyForm)

        then:
        1 * commandGatewayMock.sendAndWait(
                SurveyCommands.Start.of(
                        EventId.of("eventId"), desiredInterestThreshold, desiredEndingDate
                )
        )

    }
}

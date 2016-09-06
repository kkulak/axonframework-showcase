package knbit.events.bc.rabbitmq

import knbit.events.bc.common.dispatcher.HeaderNotificationTagAppender
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessagePropertiesBuilder
import spock.lang.Specification

import static knbit.events.bc.common.dispatcher.HeaderNotificationTagAppender.NOTIFICATION_TYPE_KEY

/**
 * Created by novy on 30.06.15.
 */
class HeaderNotificationTagAppenderTest extends Specification {

    def "should append proper notification type to message header"() {

        given:
        def objectUnderTest = new HeaderNotificationTagAppender("dummy-notification-type")

        when:
        def messagePayload = "payload".getBytes()
        def messageProperties = MessagePropertiesBuilder.newInstance().build();
        def message = new Message(messagePayload, messageProperties)
        def postProcessedMessage = objectUnderTest.postProcessMessage(message)

        then:
        def headers = postProcessedMessage.messageProperties.getHeaders();
        headers[NOTIFICATION_TYPE_KEY] == "dummy-notification-type"
    }
}

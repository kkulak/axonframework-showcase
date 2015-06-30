package knbit.notification.bc.listener

import knbit.notification.bc.messagewrapper.domain.MessageWrapper
import knbit.notification.bc.messagewrapper.infrastructure.dispatcher.NotificationDispatcher
import knbit.notification.bc.messagewrapper.infrastructure.persistence.MessageWrapperRepository
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageProperties
import spock.lang.Specification

/**
 * Created by novy on 30.06.15.
 */
class MessageListenerTest extends Specification {

    def "should wrap Message with notification tag before dispatching"() {

        given:
        def repositoryMock = Mock(MessageWrapperRepository.class)
        def notificationDispatcherMock = Mock(NotificationDispatcher.class)
        def objectUnderTest = new MessageListener(repositoryMock, notificationDispatcherMock)
        def expectedNotificationType = "NOTIFICATION_TYPE"

        when:
        def MessageProperties messageProperties = propertiesWithNotificationTypeSet(expectedNotificationType)
        def message = new Message("payload".getBytes(), messageProperties)

        objectUnderTest.receiveMessage(message)

        then:
        1 * notificationDispatcherMock.dispatch({
            it.type == expectedNotificationType
        } as MessageWrapper)

    }

    def "should throw an exception if notification tag is not specified"() {

        given:
        def repositoryMock = Mock(MessageWrapperRepository.class)
        def notificationDispatcherMock = Mock(NotificationDispatcher.class)
        def objectUnderTest = new MessageListener(repositoryMock, notificationDispatcherMock)

        when:
        objectUnderTest.receiveMessage(
                new Message("payload".getBytes(), new MessageProperties())
        )

        then:
        thrown(NullPointerException.class)

    }

    def MessageProperties propertiesWithNotificationTypeSet(String expectedNotificationType) {
        def messageProperties = new MessageProperties()
        messageProperties.setHeader("notification-type", expectedNotificationType)
        messageProperties
    }
}

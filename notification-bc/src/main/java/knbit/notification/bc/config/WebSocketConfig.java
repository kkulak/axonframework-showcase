package knbit.notification.bc.config;

import knbit.notification.bc.config.auth.WebSocketSecurityHandshakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    private static final String APP_PREFIX = "/notification-bc";

    @Autowired
    private WebSocketSecurityHandshakeInterceptor handshakeInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(Topic.PREFIX);
        config.setApplicationDestinationPrefixes(APP_PREFIX);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint(Endpoint.INITIAL).setAllowedOrigins("*").withSockJS().setInterceptors(handshakeInterceptor);
//        registry.addEndpoint(Endpoint.BATCH).setAllowedOrigins("*").withSockJS().setInterceptors(handshakeInterceptor);
//        registry.addEndpoint(Endpoint.MESSAGE_STATE).setAllowedOrigins("*").withSockJS().setInterceptors(handshakeInterceptor);
registry.addEndpoint(Endpoint.INITIAL).setAllowedOrigins("*").withSockJS();
        registry.addEndpoint(Endpoint.BATCH).setAllowedOrigins("*").withSockJS();
        registry.addEndpoint(Endpoint.MESSAGE_STATE).setAllowedOrigins("*").withSockJS();
    }

}

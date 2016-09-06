package knbit.notification.bc.config.auth;

import knbit.events.bc.auth.Role;
import knbit.events.bc.auth.aabcclient.clients.AABCClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by novy on 26.07.15.
 */

@Configuration
public class WebSocketAuthConfig {

    @Bean
    public WebSocketSecurityHandshakeInterceptor handshakeInterceptor(AABCClient aabcClient,
                                                                      @Value("${aa-bc.authHeaderKey}") String tokenKey) {

        return new WebSocketSecurityHandshakeInterceptor(aabcClient, tokenKey, Role.ADMIN);
    }
}

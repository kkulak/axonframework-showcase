package knbit.events.bc.auth.config;

import knbit.events.bc.auth.SecurityInterceptor;
import knbit.events.bc.auth.aabcclient.clients.AABCClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by novy on 26.07.15.
 */

@Slf4j
@Configuration
class InterceptorConfig {

    @Bean
    public SecurityInterceptor securityInterceptor(AABCClient aabcClient,
                                                   @Value("${aa-bc.authHeaderKey}") String authHeaderKey) {

        log.debug("creating interceptor with authHeaderKey: {}", authHeaderKey);
        return new SecurityInterceptor(aabcClient, authHeaderKey);
    }
}

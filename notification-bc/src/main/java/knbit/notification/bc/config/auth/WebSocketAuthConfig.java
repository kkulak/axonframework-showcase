package knbit.notification.bc.config.auth;

import knbit.events.bc.auth.Role;
import knbit.events.bc.auth.aabcclient.clients.AABCClient;
import knbit.events.bc.common.config.CorsFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @Configuration
    static class NarrowedCorsFilter implements Filter {

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            // todo: find a better way...
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, HEAD, OPTIONS, PATCH");
            response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, knbit-aa-auth");
            filterChain.doFilter(servletRequest, servletResponse);
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void destroy() {

        }
    }
}

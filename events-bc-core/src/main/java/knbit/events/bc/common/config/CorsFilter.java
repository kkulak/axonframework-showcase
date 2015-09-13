package knbit.events.bc.common.config;

import com.google.common.collect.ImmutableList;
import com.lambdista.util.Try;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Created by novy on 08.04.15.
 */

@Configuration
public class CorsFilter implements Filter {

    public static List<String> ALLOWED_DOMAINS = ImmutableList.of(
            "rossum.knbit.edu.pl",
            "localhost"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, HEAD, OPTIONS, PATCH, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, knbit-aa-auth");
        response.setHeader("Access-Control-Allow-Origin", accessOriginBasedOn(request));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

    private String accessOriginBasedOn(HttpServletRequest request) {
        final String originUrlString = request.getHeader("Origin");
        return shouldPreserveOrigin(originUrlString) ? originUrlString : defaultOrigin();
    }

    private boolean shouldPreserveOrigin(String origin) {
        return Try
                .apply(() -> URI.create(origin))
                .map(URI::getHost)
                .toOptional()
                .map(ALLOWED_DOMAINS::contains)
                .orElse(Boolean.FALSE);
    }

    private String defaultOrigin() {
        return String.format(
                "http://%s", ALLOWED_DOMAINS.get(0)
        );
    }
}

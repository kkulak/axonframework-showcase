package knbit.events.bc.auth.config;

import knbit.events.bc.auth.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by novy on 26.07.15.
 */

@Configuration
@Import({
        AABCClientConfig.class,
        InterceptorConfig.class
})
public class AAConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SecurityInterceptor securityException;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityException);
        super.addInterceptors(registry);
    }
}

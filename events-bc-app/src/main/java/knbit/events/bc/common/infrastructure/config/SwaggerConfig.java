package knbit.events.bc.common.infrastructure.config;

import com.mangofactory.swagger.plugin.EnableSwagger;
import org.ajar.swaggermvcui.SwaggerSpringMvcUi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by novy on 29.03.15.
 */

@EnableSwagger
@Configuration
@ComponentScan(basePackages = {"knbit.events.bc"})
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                SwaggerSpringMvcUi.WEB_JAR_RESOURCE_PATTERNS)
                .addResourceLocations(
                        SwaggerSpringMvcUi.WEB_JAR_RESOURCE_LOCATION)
                .setCachePeriod(0);
    }

    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix(SwaggerSpringMvcUi.WEB_JAR_VIEW_RESOLVER_PREFIX);
        resolver.setSuffix(SwaggerSpringMvcUi.WEB_JAR_VIEW_RESOLVER_SUFFIX);
        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}

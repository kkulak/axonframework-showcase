package knbit.events.bc.common.domain.mailnotifications;

import freemarker.cache.ClassTemplateLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by novy on 06.12.15.
 */

// todo: for some reason bean provided by spring boot doesn't find templates, fix it later
@Configuration
public class FreemarkerConfig {

    @Bean
    @Primary
    freemarker.template.Configuration freemarkerConfiguration() {
        final freemarker.template.Configuration configuration =
                new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);

        final ClassTemplateLoader classpathTemplateLoader =
                new ClassTemplateLoader(ClassLoader.getSystemClassLoader(), ".");

        configuration.setTemplateLoader(classpathTemplateLoader);

        return configuration;
    }
}

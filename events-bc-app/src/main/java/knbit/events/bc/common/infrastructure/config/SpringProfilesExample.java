package knbit.events.bc.common.infrastructure.config;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by novy on 25.07.15.
 */

@Configuration
public class SpringProfilesExample {

    @Autowired
    private ProfiledBeanWrapper.ClassToInject classToInject;

    @Configuration
    @Log4j
    static class ProfiledBeanWrapper {

        static class ClassToInject {}

        @Bean
        @Profile("mock")
        public ClassToInject mockBean() {
            log.debug("creating mock bean");
            return new ClassToInject();
        }

        @Bean
        @Profile("!mock")
        public ClassToInject otherBean() {
            log.debug("i'm not in a mock profile");
            return new ClassToInject();
        }

    }

}

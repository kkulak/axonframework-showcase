package knbit.events.bc.common.infrastructure.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by novy on 01.06.15.
 */

@Configuration
public class JacksonConfig {

    @Primary
    @Bean
    public ObjectMapper objectMapper(Jdk8Module jdk8Module, JodaModule jodaModule) {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModules(jdk8Module, jodaModule);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Bean
    public Jdk8Module jdk8Module() {
        return new Jdk8Module();
    }

    @Bean
    public JodaModule jodaModule() {
        return new JodaModule();
    }

}

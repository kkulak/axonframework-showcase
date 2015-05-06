package knbit.events.bc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by novy on 06.05.15.
 */

@Configuration
@EnableJpaRepositories(basePackages = "knbit.events.bc")
public class ViewModelConfiguration {
}

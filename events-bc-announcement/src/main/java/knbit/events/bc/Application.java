package knbit.events.bc;

import knbit.events.bc.auth.config.AAConfig;
import knbit.events.bc.common.config.CorsFilter;
import knbit.events.bc.common.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "knbit.events.bc.announcement")
@Import({
        CorsFilter.class,
        SwaggerConfig.class,
        AAConfig.class
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

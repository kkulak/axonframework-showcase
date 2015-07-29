package knbit.notification.bc;

import knbit.events.bc.auth.config.AAConfig;
import knbit.events.bc.common.config.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        AAConfig.class,
        CorsFilter.class
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

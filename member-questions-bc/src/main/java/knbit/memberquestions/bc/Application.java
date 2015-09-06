package knbit.memberquestions.bc;

import knbit.events.bc.auth.config.AAConfig;
import knbit.events.bc.common.config.CorsFilter;
import knbit.events.bc.common.config.RabbitMQConfig;
import knbit.events.bc.common.config.SwaggerConfig;
import knbit.events.bc.common.dispatcher.MessageDispatcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Created by novy on 30.06.15.
 */

@SpringBootApplication
@Import({
        CorsFilter.class,
        SwaggerConfig.class,
        AAConfig.class,
        RabbitMQConfig.class,
        MessageDispatcher.class
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

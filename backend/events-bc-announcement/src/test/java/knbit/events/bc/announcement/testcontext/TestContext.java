package knbit.events.bc.announcement.testcontext;

import knbit.events.bc.announcement.Publisher;
import knbit.events.bc.announcement.web.AnnouncementController;
import knbit.events.bc.announcement.web.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import static org.mockito.Mockito.mock;

/**
 * Created by novy on 03.04.15.
 */

@SpringBootApplication
@ComponentScan(basePackages = "knbit.events.bc.announcement.testcontext")
public class TestContext {

    @Bean
    @Autowired
    public AnnouncementController announcementController(Publisher publisher) {
        return new AnnouncementController(publisher);
    }

    @Bean
    public Publisher publisherMock() {
        return mock(Publisher.class);
    }

    @Bean
    public ExceptionHandler exceptionHandler() {
        return new ExceptionHandler();
    }
}

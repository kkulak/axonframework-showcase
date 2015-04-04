package knbit.events.bc.announcement.web;

import knbit.events.bc.announcement.Publisher;
import knbit.events.bc.announcement.config.Publishers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

/**
 * Created by novy on 03.04.15.
 */

@SpringBootApplication
class TestContext {

    @Bean
    @Autowired
    public AnnouncementController announcementController(Publisher publisher) {
        return new AnnouncementController(publisher);
    }

    @Bean(name = Publishers.COORDINATOR)
    public Publisher coordinatorMock() {
        return mock(Publisher.class);
    }
}

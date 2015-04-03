package knbit.events.bc.announcement;

import knbit.events.bc.announcement.config.Publishers;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by novy on 03.04.15.
 */

@RestController
public class AnnouncementEndpoint {

    private final Publisher publisher;


    @Autowired
    public AnnouncementEndpoint(@Qualifier(Publishers.COORDINATOR) Publisher publisher) {
        this.publisher = publisher;
    }

    @RequestMapping(value = "/announcements", method = RequestMethod.POST)
    public void postAnnouncement(@RequestBody AnnouncementDTO announcementDTO) {
        publisher.publish(
                new Announcement(announcementDTO.getTitle(), announcementDTO.getContent())
        );
    }

    // todo: probably unnecessary right now
    @Data
    @NoArgsConstructor
    private static class AnnouncementDTO {
        private String title;
        private String content;
    }

}

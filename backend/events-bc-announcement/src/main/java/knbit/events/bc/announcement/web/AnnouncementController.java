package knbit.events.bc.announcement.web;

import knbit.events.bc.announcement.Announcement;
import knbit.events.bc.announcement.Publisher;
import knbit.events.bc.announcement.config.Publishers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by novy on 03.04.15.
 */

@RestController
public class AnnouncementController {

    private final Publisher publisher;

    @Autowired
    public AnnouncementController(@Qualifier(Publishers.IIET_BOARD) Publisher publisher) {
        this.publisher = publisher;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/announcements", method = RequestMethod.POST)
    public void postAnnouncement(@RequestBody @Valid AnnouncementDTO announcementDTO) {
        publisher.publish(
                new Announcement(announcementDTO.getTitle(), announcementDTO.getContent())
        );
    }

}

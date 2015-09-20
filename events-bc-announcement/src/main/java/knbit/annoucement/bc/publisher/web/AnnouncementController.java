package knbit.annoucement.bc.publisher.web;

import knbit.annoucement.bc.publisher.Announcement;
import knbit.annoucement.bc.publisher.Publisher;
import knbit.annoucement.bc.config.PublisherFactory;
import knbit.annoucement.bc.config.Publishers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Created by novy on 03.04.15.
 */

@RestController
public class AnnouncementController {

    private final PublisherFactory factory;

    @Autowired
    public AnnouncementController(PublisherFactory factory) {
        this.factory = factory;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/announcements", method = RequestMethod.POST)
    public void postAnnouncement(@RequestBody @Valid AnnouncementDTO announcementDTO) {
        final Collection<Publisher> publishers = factory.byVendors(
                Publishers.fromStringValues(
                        announcementDTO.getPublishers()
                )
        );

        final Announcement announcement = new Announcement(
                announcementDTO.getTitle(), announcementDTO.getContent(), announcementDTO.getImageUrl()
        );

        publishers.forEach(
                publisher -> publisher.publish(announcement)
        );
    }

}

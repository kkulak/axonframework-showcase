package knbit.events.bc.announcement.web;

import knbit.events.bc.announcement.Announcement;
import knbit.events.bc.announcement.Publisher;
import knbit.events.bc.announcement.PublisherFactory;
import knbit.events.bc.announcement.PublisherVendor;
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
                PublisherVendor.fromStringValues(
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

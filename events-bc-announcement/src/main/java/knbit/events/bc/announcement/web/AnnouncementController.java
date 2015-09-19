package knbit.events.bc.announcement.web;

import knbit.events.bc.announcement.Announcement;
import knbit.events.bc.announcement.configuration.AllConfigurationQuery;
import knbit.events.bc.announcement.configuration.PublisherConfiguration;
import knbit.events.bc.announcement.publishers.Publisher;
import knbit.events.bc.announcement.publishers.PublisherFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by novy on 03.04.15.
 */

@RestController
@AllArgsConstructor
public class AnnouncementController {

    private final PublisherFactory factory;
    private final AllConfigurationQuery configurationQuery;
    private final AggregatingPublisher aggregatingPublisher;

    @Autowired
    public AnnouncementController(PublisherFactory factory, AllConfigurationQuery configurationQuery) {
        this.factory = factory;
        this.configurationQuery = configurationQuery;
        this.aggregatingPublisher = new AggregatingPublisher();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/announcements", method = RequestMethod.POST)
    public void postAnnouncement(@RequestBody @Valid AnnouncementDTO announcementDTO) {
        final Collection<ConfigurationIdAndVendor> idAndVendors =
                announcementDTO.getPublishers().isEmpty() ? defaults() : announcementDTO.getPublishers();

        final Collection<Publisher> publishers = factory.byIdsAndVendors(idAndVendors);

        final Announcement announcement = new Announcement(
                announcementDTO.getTitle(), announcementDTO.getContent(), announcementDTO.getImageUrl()
        );

        aggregatingPublisher.publish(publishers, announcement);
    }

    private Collection<ConfigurationIdAndVendor> defaults() {
        return configurationQuery.defaults()
                .stream()
                .map(this::idAndVendorFrom)
                .collect(Collectors.toList());
    }

    private ConfigurationIdAndVendor idAndVendorFrom(PublisherConfiguration configuration) {
        return new ConfigurationIdAndVendor(
                configuration.getId(), configuration.getVendor()
        );
    }

}

class AggregatingPublisher {

    public void publish(Collection<Publisher> publishers, Announcement announcementToPublish) {
        publishers.forEach(
                publisher -> publisher.publish(announcementToPublish)
        );
    }
}


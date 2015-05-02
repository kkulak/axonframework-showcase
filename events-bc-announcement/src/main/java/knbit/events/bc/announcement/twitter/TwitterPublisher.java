package knbit.events.bc.announcement.twitter;

import knbit.events.bc.announcement.Announcement;
import knbit.events.bc.announcement.Publisher;
import lombok.extern.log4j.Log4j;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

/**
 * Created by novy on 02.04.15.
 */

@Log4j
public class TwitterPublisher implements Publisher {

    private Twitter twitter;

    public TwitterPublisher(Twitter twitter) {
        this.twitter = twitter;
    }

    @Override
    public void publish(Announcement announcement) {
        try {
            final StatusUpdate newStatus = new StatusUpdate(
                    announcement.content()
            );

            final Optional<String> possibleImageUrl = announcement.imageUrl();
            if (possibleImageUrl.isPresent()) {

                final String imageName = announcement.imageName().get();

                final InputStream imageStream = createImageStreamFrom(
                        possibleImageUrl.get()
                );
                newStatus.setMedia(imageName, imageStream);
            }

            twitter.updateStatus(newStatus);

        } catch (TwitterException | IOException cause) {
            log.error(cause);
            throw new CannotPostOnTwitterException(cause);
        }
    }

    private InputStream createImageStreamFrom(String imageUrlString) throws IOException {
        return new URL(imageUrlString).openStream();

    }
}

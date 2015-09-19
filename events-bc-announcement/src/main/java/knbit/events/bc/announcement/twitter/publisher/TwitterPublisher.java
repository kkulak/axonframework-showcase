package knbit.events.bc.announcement.twitter.publisher;

import knbit.events.bc.announcement.Announcement;
import knbit.events.bc.announcement.Publisher;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public class TwitterPublisher implements Publisher {

    private Twitter twitter;
    private ImageStreamReader imageStreamReader;

    TwitterPublisher(Twitter twitter) {
        this.twitter = twitter;
        this.imageStreamReader = new ImageStreamReader();
    }

    TwitterPublisher(Twitter twitter, ImageStreamReader imageStreamReader) {
        this.twitter = twitter;
        this.imageStreamReader = imageStreamReader;
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

                final InputStream imageStream = imageStreamReader.createImageStreamFrom(
                        possibleImageUrl.get()
                );
                newStatus.setMedia(imageName, imageStream);
            }

            twitter.updateStatus(newStatus);

        } catch (TwitterException | IOException cause) {
            log.error("CannotPostOnTwitter", cause);
            throw new CannotPostOnTwitterException(cause);
        }
    }


    static class ImageStreamReader {

        public InputStream createImageStreamFrom(String imageUrlString) throws IOException {
            return new URL(imageUrlString).openStream();

        }
    }
}

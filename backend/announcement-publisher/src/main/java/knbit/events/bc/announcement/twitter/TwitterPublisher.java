package knbit.events.bc.announcement.twitter;

import knbit.events.bc.announcement.Announcement;
import knbit.events.bc.announcement.Publisher;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by novy on 02.04.15.
 */
public class TwitterPublisher implements Publisher {

    private Twitter twitter;

    public TwitterPublisher(Twitter twitter) {
        this.twitter = twitter;
    }

    @Override
    public void publish(Announcement announcement) {
        try {
            twitter.updateStatus(announcement.content());
        } catch (TwitterException e) {
            throw new CannotPostOnTwitterException();
        }
    }
}

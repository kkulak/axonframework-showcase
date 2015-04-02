package knbit.events.bc.announcement.facebook;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import knbit.events.bc.announcement.Announcement;
import knbit.events.bc.announcement.Publisher;
import twitter4j.TwitterFactory;

/**
 * Created by novy on 02.04.15.
 */
public class FacebookPublisher implements Publisher {

    private Facebook facebook;

    public FacebookPublisher(Facebook facebook) {
        this.facebook = facebook;
    }

    @Override
    public void publish(Announcement announcement) {
        try {
            facebook.postStatusMessage(announcement.content());
        } catch (FacebookException e) {
            e.printStackTrace();
        }
    }
}

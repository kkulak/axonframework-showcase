package knbit.events.bc.announcement.facebook;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.PostUpdate;
import knbit.events.bc.announcement.Announcement;
import knbit.events.bc.announcement.Publisher;
import lombok.extern.log4j.Log4j;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

/**
 * Created by novy on 02.04.15.
 */

@Log4j
public class FacebookPublisher implements Publisher {

    private Facebook facebook;

    public FacebookPublisher(Facebook facebook) {
        this.facebook = facebook;
    }

    @Override
    public void publish(Announcement announcement) {
        try {
            final PostUpdate newPost = new PostUpdate(
                    announcement.content()
            );

            final Optional<String> possibleImageUrl = announcement.imageUrl();
            if (possibleImageUrl.isPresent()) {
                final URL imageUrl = new URL(possibleImageUrl.get());
                newPost.setPicture(imageUrl);
            }

            facebook.postFeed(newPost);

        } catch (FacebookException cause) {
            log.error(cause);
            throw new CannotPostOnFacebookException(cause);
        } catch (MalformedURLException e) {
            // todo: fix
            e.printStackTrace();
        }
    }
}

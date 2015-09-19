package knbit.events.bc.announcement.publishers;

import com.google.common.base.Preconditions;
import knbit.events.bc.announcement.publishers.facebook.FacebookPublisherFactory;
import knbit.events.bc.announcement.publishers.googlegroup.GoogleGroupPublisherFactory;
import knbit.events.bc.announcement.publishers.iietboard.IIETBoardPublisherFactory;
import knbit.events.bc.announcement.publishers.twitter.TwitterPublisherFactory;
import knbit.events.bc.announcement.web.ConfigurationIdAndVendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by novy on 11.04.15.
 */

@Component
public class PublisherFactory {

    private final FacebookPublisherFactory facebookPublisherFactory;
    private final TwitterPublisherFactory twitterPublisherFactory;
    private final GoogleGroupPublisherFactory googleGroupPublisherFactory;
    private final IIETBoardPublisherFactory iietBoardPublisherFactory;

    @Autowired
    public PublisherFactory(FacebookPublisherFactory facebookPublisherFactory,
                            TwitterPublisherFactory twitterPublisherFactory,
                            GoogleGroupPublisherFactory googleGroupPublisherFactory,
                            IIETBoardPublisherFactory iietBoardPublisherFactory) {

        this.facebookPublisherFactory = facebookPublisherFactory;
        this.twitterPublisherFactory = twitterPublisherFactory;
        this.googleGroupPublisherFactory = googleGroupPublisherFactory;
        this.iietBoardPublisherFactory = iietBoardPublisherFactory;
    }

    public Publisher byIdAndVendor(ConfigurationIdAndVendor idAndVendor) {
        Preconditions.checkNotNull(idAndVendor);

        switch (idAndVendor.getVendor()) {
            case FACEBOOK:
                return facebookPublisherFactory.fromConfigurationBasedOn(idAndVendor.getId());
            case TWITTER:
                return twitterPublisherFactory.fromConfigurationBasedOn(idAndVendor.getId());
            case GOOGLE_GROUP:
                return googleGroupPublisherFactory.fromConfigurationBasedOn(idAndVendor.getId());
            case IIET_BOARD:
                return iietBoardPublisherFactory.fromConfigurationBasedOn(idAndVendor.getId());
            default:
                throw new IllegalArgumentException();
        }
    }

    public Collection<Publisher> byIdsAndVendors(Collection<ConfigurationIdAndVendor> idsAndVendors) {
        return idsAndVendors
                .stream()
                .map(this::byIdAndVendor)
                .collect(Collectors.toList());
    }


}

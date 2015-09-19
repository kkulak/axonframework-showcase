package knbit.events.bc.announcement;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by novy on 11.04.15.
 */

@Component
public class PublisherFactory {

    private final AllConfigurationQuery allConfigurationQuery;

    @Autowired
    public PublisherFactory(AllConfigurationQuery allConfigurationQuery) {
        this.allConfigurationQuery = allConfigurationQuery;
    }

    public Publisher byVendor(PublisherVendor vendor) {
        Preconditions.checkNotNull(vendor);

        switch (vendor) {
            case FACEBOOK:
//                return facebookPublisher();
            case TWITTER:
//                return twitterPublisher();
            case GOOGLE_GROUP:
//                return googleGroupPublisher();
            case IIET_BOARD:
//                return iietBoardPublisher();
            default:
                throw new IllegalArgumentException();
        }
    }

    public Collection<Publisher> byVendors(Collection<PublisherVendor> vendors) {
        return vendors
                .stream()
                .map(this::byVendor)
                .collect(Collectors.toList());
    }


}

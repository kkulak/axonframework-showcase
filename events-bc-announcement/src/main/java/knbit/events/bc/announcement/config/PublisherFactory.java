package knbit.events.bc.announcement.config;

import com.google.common.base.Preconditions;
import knbit.events.bc.announcement.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by novy on 11.04.15.
 */

@Component
public class PublisherFactory {

    private final ConfigurationRepository configurationRepository;

    @Autowired
    public PublisherFactory(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    public Publisher byVendor(Publishers vendor) {
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

    public Collection<Publisher> byVendors(Collection<Publishers> vendors) {
        return vendors
                .stream()
                .map(this::byVendor)
                .collect(Collectors.toList());
    }


}

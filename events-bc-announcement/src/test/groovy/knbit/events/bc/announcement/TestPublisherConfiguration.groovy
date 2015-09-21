package knbit.events.bc.announcement;

import knbit.events.bc.announcement.configuration.PublisherConfiguration;
import knbit.events.bc.announcement.publishers.PublisherVendor;

/**
 * Created by novy on 19.09.15.
 */
public class TestPublisherConfiguration implements PublisherConfiguration {
    private Long id;
    private String name;
    private boolean isDefault;
    private PublisherVendor vendor;

    TestPublisherConfiguration(Long id, String name, boolean isDefault, PublisherVendor vendor) {
        this.id = id
        this.name = name
        this.isDefault = isDefault
        this.vendor = vendor
    }

    @Override
    Long getId() {
        return id
    }

    @Override
    String getName() {
        return name
    }

    @Override
    boolean isDefaultPublisher() {
        return isDefault
    }

    @Override
    PublisherVendor getVendor() {
        return vendor
    }
}

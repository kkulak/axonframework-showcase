package knbit.events.bc.announcement.configuration;

import knbit.events.bc.announcement.publishers.PublisherVendor;

/**
 * Created by novy on 19.09.15.
 */
public interface PublisherConfiguration {

    Long getId();

    String getName();

    boolean isDefaultPublisher();

    PublisherVendor getVendor();
}

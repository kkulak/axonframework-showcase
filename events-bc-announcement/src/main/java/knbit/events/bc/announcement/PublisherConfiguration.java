package knbit.events.bc.announcement;

/**
 * Created by novy on 19.09.15.
 */
public interface PublisherConfiguration {

    Long getId();

    String getName();

    boolean isDefault();

    PublisherVendor getVendor();
}

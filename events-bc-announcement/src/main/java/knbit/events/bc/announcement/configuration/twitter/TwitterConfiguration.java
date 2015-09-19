package knbit.events.bc.announcement.configuration.twitter;

import knbit.events.bc.announcement.configuration.AbstractPublisherConfiguration;
import knbit.events.bc.announcement.configuration.PublisherConfiguration;
import knbit.events.bc.announcement.publishers.PublisherVendor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;

/**
 * Created by novy on 11.04.15.
 */

@Entity
@Getter
@Setter
public class TwitterConfiguration extends AbstractPublisherConfiguration implements PublisherConfiguration {

    @NotBlank
    private String consumerKey;
    @NotBlank
    private String consumerSecret;

    public TwitterConfiguration(Long id, String name, boolean isDefault, String consumerKey, String consumerSecret) {
        super(id, name, isDefault);
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }

    @Override
    public PublisherVendor getVendor() {
        return PublisherVendor.TWITTER;
    }
}

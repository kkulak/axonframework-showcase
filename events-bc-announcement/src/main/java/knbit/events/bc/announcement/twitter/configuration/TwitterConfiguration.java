package knbit.events.bc.announcement.twitter.configuration;

import knbit.events.bc.announcement.AbstractPublisherConfiguration;
import knbit.events.bc.announcement.PublisherConfiguration;
import knbit.events.bc.announcement.PublisherVendor;
import lombok.*;
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

package knbit.events.bc.announcement.facebook.configuration;

import knbit.events.bc.announcement.AbstractPublisherConfiguration;
import knbit.events.bc.announcement.PublisherConfiguration;
import knbit.events.bc.announcement.PublisherVendor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;

/**
 * Created by novy on 11.04.15.
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class FacebookConfiguration extends AbstractPublisherConfiguration implements PublisherConfiguration {

    public FacebookConfiguration(Long id, String name, boolean isDefault, String appId, String appSecret) {
        super(id, name, isDefault);
        this.appId = appId;
        this.appSecret = appSecret;
    }

    @NotBlank
    private String appId;
    @NotBlank
    private String appSecret;

    @Override
    public PublisherVendor getVendor() {
        return PublisherVendor.FACEBOOK;
    }
}


package knbit.events.bc.announcement.googlegroup.configuration;

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
public class GoogleGroupConfiguration extends AbstractPublisherConfiguration implements PublisherConfiguration {

    @NotBlank
    private String username;
    @NotBlank
    private String host;
    @NotBlank
    private String password;
    @NotBlank
    private String googleGroupAddress;

    public GoogleGroupConfiguration(Long id,
                                    String name,
                                    boolean isDefault,
                                    String username,
                                    String host,
                                    String password,
                                    String googleGroupAddress) {
        super(id, name, isDefault);
        this.username = username;
        this.host = host;
        this.password = password;
        this.googleGroupAddress = googleGroupAddress;
    }

    @Override
    public PublisherVendor getVendor() {
        return PublisherVendor.GOOGLE_GROUP;
    }
}

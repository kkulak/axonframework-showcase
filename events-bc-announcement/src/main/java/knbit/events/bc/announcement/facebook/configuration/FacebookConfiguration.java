package knbit.events.bc.announcement.facebook.configuration;

import knbit.events.bc.announcement.AbstractNamedConfiguration;
import lombok.*;

import javax.persistence.Entity;

/**
 * Created by novy on 11.04.15.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class FacebookConfiguration extends AbstractNamedConfiguration {

    private String appId;
    private String appSecret;
}


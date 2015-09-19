package knbit.events.bc.announcement.twitter.configuration;

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
public class TwitterConfiguration {

    private String consumerKey;
    private String consumerSecret;
}

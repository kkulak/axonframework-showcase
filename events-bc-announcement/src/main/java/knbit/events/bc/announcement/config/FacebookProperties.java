package knbit.events.bc.announcement.config;

import lombok.*;

/**
 * Created by novy on 11.04.15.
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FacebookProperties {

    private String appId;
    private String appSecret;
}


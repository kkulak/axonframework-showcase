package knbit.events.bc.announcement.config;

import lombok.*;

/**
 * Created by novy on 11.04.15.
 */

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class GoogleGroupProperties {

    private String username;
    private String host;
    private String password;
    private String googleGroupAddress;
}

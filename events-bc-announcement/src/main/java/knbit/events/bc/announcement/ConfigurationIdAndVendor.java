package knbit.events.bc.announcement;

import lombok.*;

/**
 * Created by novy on 19.09.15.
 */

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ConfigurationIdAndVendor {

    private Long id;
    private PublisherVendor vendor;
}

package knbit.events.bc.announcement.web;

import knbit.events.bc.announcement.publishers.PublisherVendor;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by novy on 19.09.15.
 */

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
public class ConfigurationIdAndVendor {

    @NotEmpty
    private Long id;
    @NotEmpty
    private PublisherVendor vendor;
}

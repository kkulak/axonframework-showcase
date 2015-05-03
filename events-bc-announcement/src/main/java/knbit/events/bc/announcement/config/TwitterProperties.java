package knbit.events.bc.announcement.config;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by novy on 11.04.15.
 */

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class TwitterProperties {

    @NotBlank
    private String consumerKey;
    @NotBlank
    private String consumerSecret;
}

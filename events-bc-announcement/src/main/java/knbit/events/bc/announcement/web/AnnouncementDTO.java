package knbit.events.bc.announcement.web;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Created by novy on 03.04.15.
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AnnouncementDTO {

    @NotNull
    private Collection<ConfigurationIdAndVendor> publishers;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String imageUrl;
}
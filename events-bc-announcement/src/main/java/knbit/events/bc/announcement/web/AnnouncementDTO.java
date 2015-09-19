package knbit.events.bc.announcement.web;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Collection;

/**
 * Created by novy on 03.04.15.
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnnouncementDTO {

    @NotEmpty
    private Collection<ConfigurationIdAndVendor> publishers;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String imageUrl;
}
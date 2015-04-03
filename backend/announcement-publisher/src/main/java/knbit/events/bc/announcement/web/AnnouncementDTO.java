package knbit.events.bc.announcement.web;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by novy on 03.04.15.
 */
@Data
@NoArgsConstructor
public class AnnouncementDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
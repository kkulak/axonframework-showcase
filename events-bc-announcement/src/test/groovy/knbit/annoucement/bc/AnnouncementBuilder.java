package knbit.annoucement.bc;

import knbit.annoucement.bc.publisher.Announcement;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 02.04.15.
 */

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "newAnnouncement")
public class AnnouncementBuilder {

    private String title = "title";
    private String content = "content";
    private String imageUrl = "http://valid.url.com/image.png";

    public Announcement build() {
        return new Announcement(title, content, imageUrl);
    }
}

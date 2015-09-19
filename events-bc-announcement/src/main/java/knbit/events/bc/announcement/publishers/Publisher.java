package knbit.events.bc.announcement.publishers;

import knbit.events.bc.announcement.Announcement;
import knbit.events.bc.announcement.AnnouncementException;

/**
 * Created by novy on 02.04.15.
 */
public interface Publisher {

    void publish(Announcement announcement) throws AnnouncementException;
}

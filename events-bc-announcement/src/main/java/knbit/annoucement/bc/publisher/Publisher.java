package knbit.annoucement.bc.publisher;

/**
 * Created by novy on 02.04.15.
 */
public interface Publisher {

    void publish(Announcement announcement) throws AnnouncementException;
}

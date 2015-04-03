package knbit.events.bc.announcement.web;

import knbit.events.bc.announcement.Announcement;
import knbit.events.bc.announcement.AnnouncementException;
import knbit.events.bc.announcement.Publisher;
import knbit.events.bc.announcement.config.Publishers;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by novy on 03.04.15.
 */

@RestController
public class AnnouncementEndpoint {

    private final Publisher publisher;


    @Autowired
    public AnnouncementEndpoint(@Qualifier(Publishers.COORDINATOR) Publisher publisher) {
        this.publisher = publisher;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/announcements", method = RequestMethod.POST)
    public void postAnnouncement(@RequestBody AnnouncementDTO announcementDTO) {
        publisher.publish(
                new Announcement(announcementDTO.getTitle(), announcementDTO.getContent())
        );
    }

    @ExceptionHandler(AnnouncementException.class)
    public ResponseEntity<ErrorResponse> handle(AnnouncementException exception) {

        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    // todo: probably unnecessary right now
    @Data
    @NoArgsConstructor
    static class AnnouncementDTO {
        private String title;
        private String content;
    }

}

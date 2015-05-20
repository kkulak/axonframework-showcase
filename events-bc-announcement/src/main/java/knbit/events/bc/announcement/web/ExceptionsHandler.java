package knbit.events.bc.announcement.web;

import knbit.events.bc.announcement.AnnouncementException;
import knbit.events.bc.common.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.StringJoiner;

/**
 * Created by novy on 03.04.15.
 */

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(AnnouncementException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorDTO handle(AnnouncementException exception) {

        return ErrorDTO.Builder.instance()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .cause(exception.getClass().getSimpleName())
                .developerMessage(exception.getMessage())
                .message("Cannot post message!")
                .build();

    }

}

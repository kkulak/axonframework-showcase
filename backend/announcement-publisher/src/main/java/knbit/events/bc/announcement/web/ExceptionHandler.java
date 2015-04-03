package knbit.events.bc.announcement.web;

import knbit.events.bc.announcement.AnnouncementException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.StringJoiner;

/**
 * Created by novy on 03.04.15.
 */

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(AnnouncementException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public
    @ResponseBody
    ErrorResponse handle(AnnouncementException exception) {

        return new ErrorResponse(
                exception.getMessage()
        );

    }


    // todo: refactor to handle error properly
    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public
    @ResponseBody
    ErrorResponse handle(ConstraintViolationException exception) {

        StringJoiner joiner = new StringJoiner("\n");
        exception.getConstraintViolations().forEach(
                constraintViolation -> joiner.add(constraintViolation.getMessage())
        );

        System.out.println(joiner.toString());

        return new ErrorResponse(
                joiner.toString()
        );
    }
}

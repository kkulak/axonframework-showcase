package knbit.events.bc.common;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

import static knbit.events.bc.common.ValidationErrorDTO.FieldErrorDTO;
import static knbit.events.bc.common.ValidationErrorDTO.GlobalErrorDTO;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ValidationErrorDTO handle(MethodArgumentNotValidException e) {
        final ValidationErrorDTO errorDTO = new ValidationErrorDTO();

        e.getBindingResult().getFieldErrors()
                .stream()
                .map(fe -> FieldErrorDTO.Builder.instance()
                            .resource(fe.getObjectName())
                            .field(fe.getField())
                            .code(fe.getCode())
                            .message("Illegal argument value.")
                            .invalidValue(findInvalidValue(fe))
                            .build()
                )
                .forEach(errorDTO::addFieldError);

        e.getBindingResult().getGlobalErrors()
                .stream()
                .map(oe -> GlobalErrorDTO.Builder.instance()
                        .resource(oe.getObjectName())
                        .code(oe.getCode())
                        .message(oe.getDefaultMessage())
                        .build()
                )
                .forEach(errorDTO::addGlobalError);

        return errorDTO;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ValidationErrorDTO handle(ConstraintViolationException e) {
        final ValidationErrorDTO errorDTO = new ValidationErrorDTO();

        e.getConstraintViolations()
                .stream()
                .map(cv -> FieldErrorDTO.Builder.instance()
                        .resource(cv.getRootBeanClass().getName())
                        .field(cv.getPropertyPath().toString())
                        .message("Field constraints violated.")
                        .invalidValue(cv.getInvalidValue().toString()).build())
                .forEach(errorDTO::addFieldError);

        return errorDTO;
    }

    private String findInvalidValue(FieldError e) {
        return e.getRejectedValue() != null ? e
                .getRejectedValue().toString() : null;
    }

}

package knbit.events.bc.announcement.web;

import lombok.Value;

/**
 * Created by novy on 03.04.15.
 */

@Value
public class ErrorResponse {

    private final String error;
    // todo: consider error code etc
}

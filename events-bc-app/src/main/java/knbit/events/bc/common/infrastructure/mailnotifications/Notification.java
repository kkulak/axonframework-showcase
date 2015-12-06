package knbit.events.bc.common.infrastructure.mailnotifications;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Created by novy on 05.12.15.
 */

@Value
@AllArgsConstructor
public class Notification {

    String subject;
    String message;
}

package knbit.events.bc.common.mailnotifications.rest;

import lombok.Value;

/**
 * Created by novy on 06.12.15.
 */

@Value
public class NotificationBCConfiguration {

    String notifyMembersEndpoint;
    String notifyAllMembersEndpoint;
}

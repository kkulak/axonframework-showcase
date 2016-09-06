package knbit.events.bc.common.infrastructure.mailnotifications.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by novy on 06.12.15.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
class NotificationBCProperties implements NotificationEndpoints {

    private String knbitServiceId;
    private String knbitPassword;
    private String notifyMembersEndpoint;
    private String notifyAllMembersEndpoint;
}

interface NotificationEndpoints {
    String getNotifyMembersEndpoint();

    String getNotifyAllMembersEndpoint();
}




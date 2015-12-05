package knbit.events.bc.common.mailnotifications;

import knbit.events.bc.enrollment.domain.valueobjects.MemberId;

import java.util.Collection;

/**
 * Created by novy on 05.12.15.
 */
public interface NotificationBCClient {

    void sendNotificationToAllMembers(Notification notification);

    void sendNotificationTo(Collection<MemberId> members, Notification notification);
}

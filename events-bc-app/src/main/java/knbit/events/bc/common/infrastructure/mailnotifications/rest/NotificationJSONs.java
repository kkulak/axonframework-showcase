package knbit.events.bc.common.infrastructure.mailnotifications.rest;

import knbit.events.bc.common.infrastructure.mailnotifications.Notification;
import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import lombok.Getter;
import lombok.Value;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by novy on 06.12.15.
 */

interface NotificationJSONs {

    interface NotificationJson {

    }

    @Getter
    class NotificationWithoutMembersJson implements NotificationJson {
        private final String subject;
        private final String message;

        public NotificationWithoutMembersJson(Notification notification) {
            this.subject = notification.getSubject();
            this.message = notification.getMessage();
        }
    }

    @Getter
    class NotificationWithMembersJson implements NotificationJson {
        private final String subject;
        private final String message;
        private final Collection<MemberIdJson> receivers;

        public NotificationWithMembersJson(Notification notification, Collection<MemberId> members) {
            this.subject = notification.getSubject();
            this.message = notification.getMessage();
            this.receivers = receiversFrom(members);
        }

        private Collection<MemberIdJson> receiversFrom(Collection<MemberId> members) {
            return members.stream()
                    .map(MemberId::value)
                    .map(MemberIdJson::new)
                    .collect(Collectors.toList());
        }

        @Value
        static class MemberIdJson {
            String userId;
        }
    }
}

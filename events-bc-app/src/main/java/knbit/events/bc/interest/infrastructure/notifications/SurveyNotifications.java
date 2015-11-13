package knbit.events.bc.interest.infrastructure.notifications;

import lombok.*;

/**
 * Created by novy on 13.11.15.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class SurveyNotifications {

    interface SurveyInterestNotification {

        String getEventId();

        String getName();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static class InterestThresholdReached implements SurveyInterestNotification {
        private String eventId;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static class SurveyingTimeExceeded implements SurveyInterestNotification {
        @NonNull
        private String eventId;
        @NonNull
        private String name;
    }
}

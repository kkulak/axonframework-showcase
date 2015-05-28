package knbit.events.bc.interestsurvey.domain.policies;

import lombok.EqualsAndHashCode;

/**
 * Created by novy on 28.05.15.
 */

@EqualsAndHashCode
public class InterestThresholdTurnedOffPolicy implements InterestPolicy {

    @Override
    public boolean reachedBy(int actualInterest) {
        return false;
    }
}

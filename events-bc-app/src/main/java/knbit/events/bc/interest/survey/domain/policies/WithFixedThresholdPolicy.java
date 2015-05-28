package knbit.events.bc.interest.survey.domain.policies;

import lombok.EqualsAndHashCode;

/**
 * Created by novy on 28.05.15.
 */

@EqualsAndHashCode
public class WithFixedThresholdPolicy implements InterestPolicy {

    private final int minimalInterestThreshold;

    public WithFixedThresholdPolicy(int minimalInterestThreshold) {
        this.minimalInterestThreshold = minimalInterestThreshold;
    }

    @Override
    public boolean reachedBy(int actualInterest) {
        return actualInterest == minimalInterestThreshold;
    }
}

package knbit.events.bc.interestsurvey.domain.policies;

/**
 * Created by novy on 28.05.15.
 */
public interface InterestThresholdPolicy {

    boolean interestThresholdReached(int actualInterest);
}

package knbit.events.bc.interestsurvey.domain.policies;

/**
 * Created by novy on 28.05.15.
 */
public interface InterestPolicy {

    boolean reachedBy(int actualInterest);
}

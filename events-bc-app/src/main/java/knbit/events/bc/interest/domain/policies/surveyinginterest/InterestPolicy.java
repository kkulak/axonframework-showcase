package knbit.events.bc.interest.domain.policies.surveyinginterest;

/**
 * Created by novy on 28.05.15.
 */
public interface InterestPolicy {

    boolean reachedBy(int actualInterest);
}

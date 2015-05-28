package knbit.events.bc.interestsurvey.domain.valueobjects;

import knbit.events.bc.common.domain.UUIDBasedIdentifier;

/**
 * Created by novy on 28.05.15.
 */
public class SurveyId extends UUIDBasedIdentifier {

    public SurveyId() {
        super();
    }

    protected SurveyId(String value) {
        super(value);
    }

    public static SurveyId of(String value) {
        return new SurveyId(value);
    }
}

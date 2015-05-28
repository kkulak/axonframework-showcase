package knbit.events.bc.interest.questionnaire.domain.valueobjects.ids;

import knbit.events.bc.common.domain.UUIDBasedIdentifier;

/**
 * Created by novy on 26.05.15.
 */
public class QuestionId extends UUIDBasedIdentifier {

    public QuestionId() {
        super();
    }

    protected QuestionId(String id) {
        super(id);
    }

    public static QuestionId of(String id) {
        return new QuestionId(id);
    }
}

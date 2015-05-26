package knbit.events.bc.questionnaire.domain.valueobjects.ids;

import knbit.events.bc.common.domain.UUIDBasedIdentifier;

/**
 * Created by novy on 25.05.15.
 */
public class QuestionnaireId extends UUIDBasedIdentifier {

    public QuestionnaireId() {
        super();
    }

    protected QuestionnaireId(String id) {
        super(id);
    }

    public static QuestionnaireId of(String id) {
        return new QuestionnaireId(id);
    }
}

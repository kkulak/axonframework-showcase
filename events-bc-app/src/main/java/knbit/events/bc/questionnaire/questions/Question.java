package knbit.events.bc.questionnaire.questions;

import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 25.05.15.
 */


@Accessors(fluent = true)
@Value
abstract public class Question {

    private final String title;
    private final String description;

}

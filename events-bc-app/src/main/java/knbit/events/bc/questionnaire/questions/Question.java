package knbit.events.bc.questionnaire.questions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 25.05.15.
 */


@Accessors(fluent = true)
@Getter
@RequiredArgsConstructor
abstract public class Question {

    private final String title;
    private final String description;

}

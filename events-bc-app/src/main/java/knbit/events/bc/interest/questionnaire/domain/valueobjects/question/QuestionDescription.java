package knbit.events.bc.interest.questionnaire.domain.valueobjects.question;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by novy on 26.05.15.
 */

@Accessors(fluent = true)
@Getter
@EqualsAndHashCode
@ToString
public class QuestionDescription {

    private final String value;

    private QuestionDescription(String value) {
        // todo: length?
        Preconditions.checkArgument(
                !Strings.isNullOrEmpty(value)
        );
        this.value = value;
    }

    public static QuestionDescription of(String value) {
        return new QuestionDescription(value);
    }
}

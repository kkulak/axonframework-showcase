package knbit.memberquestions.bc.question.answering.service;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.apache.commons.validator.routines.EmailValidator;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Accessors(fluent = true)
public class Email {

    private final String email;
    private final String subject;
    private final String content;

    public static Email of(String email, String subject, String content) {
        Preconditions.checkNotNull(email);
        Preconditions.checkNotNull(subject);
        Preconditions.checkNotNull(content);
        Preconditions.checkArgument(EmailValidator.getInstance()
                .isValid(email), "Invalid email address!");

        return new Email(email, subject, content);
    }


}

package knbit.memberquestions.bc.askingquestion;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by novy on 30.06.15.
 */

@Data
@NoArgsConstructor
public class Question {

    @NotBlank
    private String eventName;
    @NotBlank
    private String topic;
    @NotBlank
    private String content;
    @Email
    private String email;
}

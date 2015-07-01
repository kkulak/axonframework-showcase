package knbit.memberquestions.bc.question.answering;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
public class Answer {

    @Email
    private String email;
    @NotBlank
    private String subject;
    @NotBlank
    private String content;

}

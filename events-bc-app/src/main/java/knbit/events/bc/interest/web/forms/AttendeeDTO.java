package knbit.events.bc.interest.web.forms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AttendeeDTO {

    @NotBlank String memberId;
    // todo: got rid of firstname and lastname later
    @NotBlank private String firstName;
    @NotBlank private String lastName;

}

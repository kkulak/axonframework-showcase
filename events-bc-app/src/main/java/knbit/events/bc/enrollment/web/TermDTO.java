package knbit.events.bc.enrollment.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by novy on 04.10.15.
 */

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
class TermDTO {

    @NotBlank
    private String termId;
    @NotNull
    private Lecturer lecturer;
    @NotNull
    private Integer participantsLimit;

    @Data
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    static class Lecturer {

        @NotBlank
        private String firstName;
        @NotBlank
        private String lastName;
    }
}

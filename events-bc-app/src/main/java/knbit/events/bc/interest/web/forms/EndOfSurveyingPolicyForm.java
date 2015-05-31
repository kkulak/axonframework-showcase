package knbit.events.bc.interest.web.forms;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EndOfSurveyingPolicyForm {

    @NotNull private LocalDateTime endOfSurveying;

}

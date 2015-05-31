package knbit.events.bc.interest.web.forms;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationPolicyForm {

    @NotNull private Integer minimalInterestThreshold;

}

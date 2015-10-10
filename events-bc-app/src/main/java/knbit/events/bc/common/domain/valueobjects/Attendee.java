package knbit.events.bc.common.domain.valueobjects;

import knbit.events.bc.enrollment.domain.valueobjects.MemberId;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 25.05.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class Attendee {

    MemberId memberId;
}

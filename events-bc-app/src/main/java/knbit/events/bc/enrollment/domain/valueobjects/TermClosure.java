package knbit.events.bc.enrollment.domain.valueobjects;

import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Collection;

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class TermClosure {
    TermId termId;
    Collection<Lecturer> lecturers;
    ParticipantsLimit participantsLimit;
}

package knbit.events.bc.choosingterm.domain.valuobjects;

import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

import java.util.Collection;

/**
 * Created by novy on 03.10.15.
 */

@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor(staticName = "of")
public class EnrollmentIdentifiedTerm {

    @Getter
    private final TermId termId;
    @Delegate
    private final Term term;
    @Getter
    private final Collection<Lecturer> lecturers;
    @Getter
    private final ParticipantsLimit participantsLimit;

}
